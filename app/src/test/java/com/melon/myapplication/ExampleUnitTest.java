package com.melon.myapplication;

import com.melon.mylibrary.util.BitOperator;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);

//        System.out.print(Long.MAX_VALUE);

        long time = 1559000000000L + 3*100000000L;
        System.out.print(time);
    }
}