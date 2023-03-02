package com.rosszhang.syncplayer.client.exception;

public class SyncPlayerException extends RuntimeException {

    public SyncPlayerException() {
    }

    public SyncPlayerException(String message) {
        super(message);
    }

    public SyncPlayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
