package com.rosszhang.syncplayer.client.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class CommonUtilsTest {

    @InjectMocks
    private CommonUtils commonUtils;

    @Test
    void calculateYTTimeToSecond() {
        int i = CommonUtils.calculateYTTimeToSecond("0 时1 分钟0秒");
        assertEquals(i, 60);
    }
}