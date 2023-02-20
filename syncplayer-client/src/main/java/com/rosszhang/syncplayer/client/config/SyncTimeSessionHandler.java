package com.rosszhang.syncplayer.client.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.util.Objects;

public class SyncTimeSessionHandler extends StompSessionHandlerAdapter {

//    private Integer diff;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("after connected...");
        String path = "/app/sync/1234";
        StompSession.Subscription subscribe = session.subscribe("/topic/synctime/1234", this);
        log.info("subscription info {} ", subscribe.toString());
        session.send(path, "111");
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        log.info("Client received message: {} ", Objects.requireNonNull(payload));
        super.handleFrame(headers, payload);
        //do sync operation
//        this.diff = (Integer) payload;
    }

//    public Integer getDiff() {
//        return diff;
//    }
}
