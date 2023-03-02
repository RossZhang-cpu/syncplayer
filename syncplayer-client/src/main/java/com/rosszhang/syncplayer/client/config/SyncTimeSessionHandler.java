package com.rosszhang.syncplayer.client.config;

import com.rosszhang.syncplayer.client.exception.SyncPlayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.util.Objects;

public class SyncTimeSessionHandler extends StompSessionHandlerAdapter {

    private Integer syncTime;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        log.info("Client received message: {} ", Objects.requireNonNull(payload));
        super.handleFrame(headers, payload);
        //do sync operation
        this.syncTime = Integer.valueOf((String) payload);
    }

    /**
     * only allow to read once
     * @return
     */
    public Integer getSyncTime() {
        Integer result = syncTime;
        if (syncTime != null) {
            syncTime = null;
        }
        return result;
    }

    public Integer listenResult() throws InterruptedException {
        Integer result;
        int i = 0;
        while ((result = getSyncTime()) == null && i < 200) {
            Thread.sleep(50);
            ++i;
        }
        if (result == null) {
            throw new SyncPlayerException("Server not respond in 10s");
        }
        return result;
    }
}
