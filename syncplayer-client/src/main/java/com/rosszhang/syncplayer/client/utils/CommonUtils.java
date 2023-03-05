package com.rosszhang.syncplayer.client.utils;

import java.util.Optional;

public class CommonUtils {

    public static int calculateToSecond(String curTime) {
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

    public static int calculateYTTimeToSecond(String curTime) {
        String time = Optional.ofNullable(curTime).map(v -> v.replaceAll("\\s+", "")).orElse("");
        int hours = 0, minutes = 0, seconds = 0;
        if (time.contains("时")) {
            String[] hourStr = time.split("时");
            hours = Integer.parseInt(hourStr[0]);
            time = hourStr[1];
        }
        if (time.contains("分钟")) {
            String[] minuteStr = time.split("分钟");
            minutes = Integer.parseInt(minuteStr[0]);
            time = minuteStr[1];
        }
        if (time.contains("秒")) {
            String[] secondStr = time.split("秒");
            seconds = Integer.parseInt(secondStr[0]);
        }

        return hours * 3600 + minutes * 60 + seconds;
    }
}
