package com.chryl.tcp.client.controller;

import com.chryl.tcp.client.RequestPacket;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tio.core.Tio;

import java.io.UnsupportedEncodingException;

import static com.chryl.tcp.client.ClientStarter.clientChannelContext;


/**
 * Created By Chr on 2019/4/28.
 */
@RestController
@RequestMapping("/tio")
public class Controller {

    /**
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = {"/time"}, method = RequestMethod.GET)
    public String show1() throws UnsupportedEncodingException {
        RequestPacket requestPacket = new RequestPacket();


        requestPacket.setBody("97 79 00 00 00 0F 50 10 00 00 00 04 08 20 17 10 10 08 55 00 02 "
                .trim().replace(" ", "").getBytes(RequestPacket.CHARSET));


//            Tio.bindUser(clientChannelContext, "tio-client-01");
        Tio.bindUser(clientChannelContext, "client-01");
        Tio.send(clientChannelContext, requestPacket);
        return "suc:time";

    }

    //时间
    @RequestMapping(value = {"/heart"}, method = RequestMethod.GET)
    public String show2() throws UnsupportedEncodingException {
        RequestPacket requestPacket = new RequestPacket();

        requestPacket.setBody("ABBA00000010506A1C00500300000118013701502493".trim().getBytes(RequestPacket.CHARSET));

        Tio.bindUser(clientChannelContext, "client-01");
        Tio.send(clientChannelContext, requestPacket);
        return "suc:heart";
    }

    //###############开
    //灯全开
    @RequestMapping(value = {"/1234"}, method = RequestMethod.GET)
    public String show5() throws UnsupportedEncodingException {
        RequestPacket requestPacket = new RequestPacket();

        requestPacket.setBody("97 79 00 00 00 09 50  0F   0010 0010 02 0F00".trim().replace(" ", "")
                .getBytes(RequestPacket.CHARSET));
        Tio.bindUser(clientChannelContext, "client-01");
        Tio.send(clientChannelContext, requestPacket);
        return "suc:on";
    }

    //###############关闭
    //控制
    @RequestMapping(value = {"/1", ""}, method = RequestMethod.GET)
    public String show3() throws UnsupportedEncodingException {
        RequestPacket requestPacket = new RequestPacket();

        requestPacket.setBody("97 79 00 00 00 09 50  0F   0010 0010 02 0100".trim().replace(" ", "")
                .getBytes(RequestPacket.CHARSET));
        Tio.bindUser(clientChannelContext, "client-01");
        Tio.send(clientChannelContext, requestPacket);
        return "suc:1-";
    }

    //控制
    @RequestMapping(value = {"/3", ""}, method = RequestMethod.GET)
    public String show4() throws UnsupportedEncodingException {
        RequestPacket requestPacket = new RequestPacket();

        requestPacket.setBody("97 79 00 00 00 09 50  0F   0010 0010 02 0400".trim().replace(" ", "")
                .getBytes(RequestPacket.CHARSET));
        Tio.bindUser(clientChannelContext, "client-01");
        Tio.send(clientChannelContext, requestPacket);
        return "suc:3-";
    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    /**
     * 当 ByteBuffer.remaining()  小于要读取或写入的长度时，再执行读取或写入操作都会产生异常；
     * <p>
     * 读取则产生 java.nio.BufferUnderflowException 异常，
     * <p>
     * 写入则产生 java.nio.BufferOverflowException 异常。
     * <p>
     * 当 ByteBuffer.remaining()  等于 0 时，不能再执行读取或写入操作，需要执行：clear() 操作，否则将产生异常。
     *
     * @param args
     */

    public static void main(String args[]) {
        String s = "    97 79 00 00 00 09 50  0F   0010 0010 02 0100 - 1".trim().replace(" ", "");
        System.out.println(s);
//        ByteBuffer byteBuffer = ByteBuffer.allocate(42 * 2);
        /*int remaining = byteBuffer.remaining();
        if (remaining >= s.length()) {
            //就不会出现:Exception in thread "main" java.nio.BufferOverflowException
        }*/
//        byteBuffer.put(bytes);//
//        byteBuffer.putInt(s.length());
//        System.out.println(bytes);
    }
}
