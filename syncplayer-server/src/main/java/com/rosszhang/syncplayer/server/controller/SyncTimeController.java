package com.rosszhang.syncplayer.server.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;

@Controller
public class SyncTimeController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private AtomicInteger count = new AtomicInteger(0);
    private Map<String, List<Integer>> timeMap = new ConcurrentHashMap<>();
    private final String regex = "[0-5][0-9]:[0-5][0-9]";

    @MessageMapping("/sync/1234")
    @SendTo("/topic/synctime/1234")
    public String broadcastCurrentTime(String roomId, @Payload String currentTime) {
        log.info("received message roomId {} currentTime {} ", roomId, currentTime);
        int i = count.incrementAndGet();
        List<Integer> list = timeMap.getOrDefault(roomId, new ArrayList<>());
        if ( i > 2) {
            //missed some messages
            //todo
        }
        //receive new message
        int secondes = Integer.parseInt(currentTime);
        list.add(secondes);
        Integer syncTime = 0;
        if (i == 2) {
            //send sync message
            Integer[] maxAndMin = findMaxAndMin(list);
            syncTime = computeSyncTime(maxAndMin[0], maxAndMin[1]);
            count.set(0);
            timeMap.remove(roomId);
        }else{
            syncTime = secondes;
        }
        log.info("syncTime is {}", syncTime);
        return String.valueOf(syncTime);
    }

    private Integer computeSyncTime(Integer maxTime, Integer minTime) {
        int diff = maxTime - minTime;
        return diff > 3 ? diff : 0;
    }

    private Integer[] findMaxAndMin(List<Integer> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new IllegalArgumentException("Input List must not be null");
        }

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (Integer number : list) {
            max = max < number ? number : max;
            min = min > number ? number : min;
        }
        return new Integer[]{max, min};
    }

}

