package com.chryl;

import com.chryl.server.start.ShowcaseWebsocketStarter;
import com.chryl.tcp.client.ClientStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TioWebsocketTest01Application {

    public static void main(String[] args) throws Exception {
        ShowcaseWebsocketStarter.start();

        SpringApplication.run(TioWebsocketTest01Application.class, args);

        ClientStarter.start();
    }

}
