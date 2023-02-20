package com.rosszhang.syncplayer.client.service;

import com.rosszhang.syncplayer.client.config.SyncTimeSessionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

//@Configuration
//@EnableWebSocketMessageBroker
public class WebSocketClientFacade {

//    @Bean

    private static final Logger log = LoggerFactory.getLogger(WebSocketClientFacade.class);
    private WebSocketStompClient stompClient;
    private StompSession session;
    private String serverEndPoint;
    private StompSessionHandlerAdapter handler;


    public WebSocketClientFacade() {
        defaultConfig();
    }

    public WebSocketClientFacade(String serverEndPoint, StompSessionHandlerAdapter handlerAdapter) {
        defaultConfig();
        this.serverEndPoint = serverEndPoint;
        this.handler = handlerAdapter;
        this.session = connectToServer(serverEndPoint);
    }

    public void defaultConfig() {
        this.stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        this.stompClient.setMessageConverter(new StringMessageConverter());
    }

    private StompSession connectToServer(String endPoint) {
        StompSession session = null;
        try {
            session = this.stompClient.connect(endPoint, handler).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getStackTrace().toString());
        }
        assert session != null;
        return session;
    }

    public String getServerEndPoint() {
        return serverEndPoint;
    }

    public void setServerEndPoint(String serverEndPoint) {
        this.serverEndPoint = serverEndPoint;
    }

    public StompSession getSession() {
        return session;
    }

    public StompSessionHandlerAdapter getHandler() {
        return handler;
    }

    public void send(String path, String message) {
        this.session.send(path, message);
    }

    public void subscribe(String subscribeUrl) {
       this.session.subscribe(subscribeUrl, Objects.requireNonNull(this.handler));
    }

}
