package com.melon.mylibrary.util;

import static org.junit.Assert.*;

public class CommonUtilTest {

    @org.junit.Test
    public void getCurrentDataTime() {
        String currentDataTime = CommonUtil.getCurrentDataTime();
        assertNotNull(currentDataTime);
    }
}