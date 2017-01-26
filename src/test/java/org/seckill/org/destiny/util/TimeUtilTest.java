package org.seckill.org.destiny.util;

import org.junit.Test;

import java.util.Date;

/**
 * designed by destiny
 *
 * @author destiny
 *         e-mail destinywk@163.com
 * @version JDK 1.8.0_101
 * @since 2017/1/26 23:54
 */
public class TimeUtilTest {

    private static final int MILLISECOND_OF_A_DAY = 1000 * 60 * 60 * 24;

    private static final int MILLISECOND_OF_DIFF = 1000 * 60 * 60 * 16;

    @Test
    public void testTimeFormat() {
        Date currentDate = new Date();
        currentDate.setTime(new Date().getTime() / MILLISECOND_OF_A_DAY * MILLISECOND_OF_A_DAY + MILLISECOND_OF_DIFF);
        System.out.println(currentDate);
    }
}
