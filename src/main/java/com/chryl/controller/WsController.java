package com.chryl.controller;

import com.chryl.server.ShowcaseWsMsgHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created By Chr on 2019/6/17.
 */
@RequestMapping("/ws")
public class WsController {

    @Autowired
    private ShowcaseWsMsgHandler showcaseWsMsgHandler;

    @RequestMapping("/1")
    public void show1() {
//        showcaseWsMsgHandler.onText();

//        String msg = "{name:'" + channelContext.userid + "',message:'" + text + "'}";
        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
//        WsResponse wsResponse = WsResponse.fromText(msg, ShowcaseServerConfig.CHARSET);
        //群发
//        Tio.sendToGroup(channelContext.groupContext, Const.GROUP_ID, wsResponse);
    }
}
