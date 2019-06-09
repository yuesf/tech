package com.yuesf.tech.lesson1.java7;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author yuesf
 * @date 19/6/9
 */
public class ConcurrentHashMapDemo {
    static Map<String, String> map = new HashMap<>();

    public static void main(String[] args) {

        //使用线程存或取查看是否支持并发操作
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    putMap();
                }
            }).start();
        }
    }

    private static void putMap() {
        for (int j = 0; j < 100; j++) {
            map.put(Thread.currentThread().getName() + j, Thread.currentThread().getName());
        }
        //做以下操作会有 ConcurrentModificationException 异常
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            System.out.println(next.getKey() + " " + next.getValue());
        }
    }


}
