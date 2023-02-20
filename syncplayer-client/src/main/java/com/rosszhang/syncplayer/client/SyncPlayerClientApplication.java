package com.rosszhang.syncplayer.client;

import com.rosszhang.syncplayer.client.config.SyncTimeSessionHandler;
import com.rosszhang.syncplayer.client.service.WebSocketClientFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncPlayerClientApplication {

    private String endPoint = "ws://localhost:9001/ws";

    private static final Logger log = LoggerFactory.getLogger(SyncPlayerClientApplication.class);


    public static void main(String[] args) throws InterruptedException {
        String serverEndPoint = "ws://localhost:9001/ws";
        String subscribeUrl = "/topic/synctime/1234";
        SyncTimeSessionHandler handler = new SyncTimeSessionHandler();
        WebSocketClientFacade clientFacade = new WebSocketClientFacade(serverEndPoint, handler);
        String path = "/app/sync/1234";
        clientFacade.subscribe(subscribeUrl);
        clientFacade.send(path, "111");
//        log.info("diff is {} ", handler.getDiff());
        Thread.sleep(10000);
    }


}
