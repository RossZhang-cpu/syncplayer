package com.rosszhang.syncplayer.client;

import com.rosszhang.syncplayer.client.config.SyncTimeSessionHandler;
import com.rosszhang.syncplayer.client.service.VideoController;
import com.rosszhang.syncplayer.client.service.WebSocketClientFacade;
import com.rosszhang.syncplayer.client.service.impl.DDRKVideoController;
import com.rosszhang.syncplayer.client.service.impl.YoutubeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncPlayerClientApplication {

    private String endPoint = "ws://localhost:9001/ws";

    private static final Logger log = LoggerFactory.getLogger(SyncPlayerClientApplication.class);


//    public static void main(String[] args) throws InterruptedException {
//        DDRKVideoController controller = new DDRKVideoController();
//        controller.openVideoWebSite();
//        controller.searchVideoByName("初恋");
//
//        String serverEndPoint = "ws://localhost:9001/ws";
//        String subscribeUrl = "/topic/synctime/1234";
//        SyncTimeSessionHandler handler = new SyncTimeSessionHandler();
//        WebSocketClientFacade clientFacade = new WebSocketClientFacade(serverEndPoint, handler);
//        String path = "/app/sync/1234";
//        clientFacade.subscribe(subscribeUrl);
//        controller.playCurrentVideo();
//        boolean isOver = false;
//        while (!isOver) {
//            Thread.sleep(5000);
//            Integer seconds = controller.listenProgress();
//            clientFacade.send(path, String.valueOf(seconds));
//            Integer result = handler.listenResult();
//            controller.syncProgress(result);
//        }
//    }

    public static void main(String[] args) throws InterruptedException {
        VideoController controller = new YoutubeController();
        controller.openVideoByUrl("https://www.youtube.com/watch?v=s-EEGKXSR3k");

        String serverEndPoint = "ws://localhost:9001/ws";
        String subscribeUrl = "/topic/synctime/1234";
//        SyncTimeSessionHandler handler = new SyncTimeSessionHandler();
//        WebSocketClientFacade clientFacade = new WebSocketClientFacade(serverEndPoint, handler);
//        String path = "/app/sync/1234";
//        clientFacade.subscribe(subscribeUrl);
        controller.playCurrentVideo();
//        boolean isOver = false;
//        while (!isOver) {
//            Thread.sleep(5000);
            Integer seconds = controller.listenProgress();
//            clientFacade.send(path, String.valueOf(seconds));
//            Integer result = handler.listenResult();
            controller.syncProgress(300);
//        }
    }


}
