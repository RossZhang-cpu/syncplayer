package com.rosszhang.syncplayer.client.utils;

import java.util.Optional;

public class CommonUtils {

    public static int caculateToSecond(String curTime) {
        String time = Optional.ofNullable(curTime).orElse("");
        String[] split = time.split(":");
        int seconds = 0;
        if (split.length == 3) {
            seconds = Integer.parseInt(split[0]) * 3600 + Integer.parseInt(split[1]) * 60 + Integer.parseInt(split[2]);
        } else if (split.length == 2) {
            seconds = Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);

        } else if (split.length == 1) {
            seconds = Integer.parseInt(split[0]);
        }
        return seconds;
    }
}
