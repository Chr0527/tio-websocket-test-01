package com.chryl.server;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static com.chryl.tcp.server.ServerHandler.map;

/**
 * Created By Chr on 2019/6/15.
 */
@Component
public class ShowcaseWsMsgHandler implements IWsMsgHandler {
    private static Logger log = LoggerFactory.getLogger(ShowcaseWsMsgHandler.class);
    public static final ShowcaseWsMsgHandler me = new ShowcaseWsMsgHandler();

    private ShowcaseWsMsgHandler() {
    }

    /**
     * 握手时走这个方法，业务可以在这里获取cookie，request参数等
     */
    @Override
    public HttpResponse handshake(HttpRequest request, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        String clientip = request.getClientIp();
        String myname = request.getParam("name");
        Tio.bindUser(channelContext, myname);
//        channelContext.setUserid(myname);
        log.info("收到来自{}的ws握手包\r\n{}", clientip, request.toString());
        System.out.println("=============================1");

        return httpResponse;
    }

    /**
     * @param httpRequest
     * @param httpResponse
     * @param channelContext
     * @throws Exception
     * @author tanyaowu
     */
    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        //绑定到群组，后面会有群发
        Tio.bindGroup(channelContext, Const.GROUP_ID);
        int count = Tio.getAllChannelContexts(channelContext.groupContext).getObj().size();
        String msg = "{name:'admin',message:'" + channelContext.userid + " 进来了，共【" + count + "】人在线" + "'}";
        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
        WsResponse wsResponse = WsResponse.fromText(msg, ShowcaseServerConfig.CHARSET);
        //群发
//        Tio.sendToGroup(channelContext.groupContext, Const.GROUP_ID, wsResponse);
        System.out.println("=============================2");


        //websocket
        //定时任务,查看该用户下的设备
        TimeTask timeTask = new TimeTask(channelContext.groupContext, channelContext.userid, Const.GROUP_ID, wsResponse);
        timer.schedule(timeTask, 0, 5000);
    }

    /**
     * 字节消息（binaryType = arraybuffer）过来后会走这个方法
     */
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        System.out.println("=============================3");


        return null;
    }

    /**
     * 当客户端发close flag时，会走这个方法
     */
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        Tio.remove(channelContext, "receive close flag");
        System.out.println("=============================4");


        return null;
    }

    /*
     * 字符消息（binaryType = blob）过来后会走这个方法
     */
    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
        WsSessionContext wsSessionContext = (WsSessionContext) channelContext.getAttribute();
        HttpRequest httpRequest = wsSessionContext.getHandshakeRequest();//获取websocket握手包
        System.out.println("=============================5");

        if (log.isDebugEnabled()) {
            log.debug("握手包:{}", httpRequest);
        }
        log.info("收到ws消息:{}", text);
        if (Objects.equals("心跳内容", text)) {
            return null;
        }
        //channelContext.getToken()
        //String msg = channelContext.getClientNode().toString() + " 说：" + text;
        String msg = "{name:'" + channelContext.userid + "',message:'" + text + "'}";
        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
        WsResponse wsResponse = WsResponse.fromText(msg, ShowcaseServerConfig.CHARSET);
        //群发
        Tio.sendToGroup(channelContext.groupContext, Const.GROUP_ID, wsResponse);


        //返回值是要发送给客户端的内容，一般都是返回null
        return null;
    }


    //定时发送给前端 数据
    Timer timer = new Timer();

    class TimeTask extends TimerTask {
        GroupContext groupContext;
        String channelContextUserId;
        String userId;
        WsResponse wsResponse;

        public TimeTask() {
        }

        public TimeTask(GroupContext groupContext, String channelContextUserId, String userId, WsResponse wsResponse) {
            this.groupContext = groupContext;
            this.channelContextUserId = channelContextUserId;
            this.userId = userId;
            this.wsResponse = wsResponse;
        }

        @Override
        public void run() {
            try {
                /**
                 * 传入user,根据user拿出sb数据
                 */
                String msg = JSONObject.toJSONString(map.get("user-01"));
                WsResponse wsR = WsResponse.fromText(msg, ShowcaseServerConfig.CHARSET);
                Tio.sendToGroup(groupContext, Const.GROUP_ID, wsR);
                //展示完移除
                map.remove("user-01");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}