package com.rosszhang.syncplayer.client.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.util.Objects;

public class SyncTimeSessionHandler extends StompSessionHandlerAdapter {

    private Integer diff;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        log.info("Client received message: {} ", Objects.requireNonNull(payload));
        super.handleFrame(headers, payload);
        //do sync operation
        this.diff = Integer.valueOf((String) payload);
    }

    /**
     * only allow to read once
     * @return
     */
    public Integer getDiff() {
        Integer result = diff;
        if (diff != null) {
            diff = null;
        }
        return result;
    }

    public Integer listenResult() throws InterruptedException {
        Integer result;
        int i = 0;
        while ((result = getDiff()) == null && i < 200) {
            Thread.sleep(50);
            ++i;
        }
        return result;
    }
}
