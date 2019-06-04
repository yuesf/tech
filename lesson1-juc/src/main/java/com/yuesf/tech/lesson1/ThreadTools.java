package com.yuesf.tech.lesson1;

/**
 * 线程工具类
 * @author yuesf
 */
public class ThreadTools {

    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
