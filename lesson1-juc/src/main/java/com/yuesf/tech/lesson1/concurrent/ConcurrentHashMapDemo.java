package com.yuesf.tech.lesson1.concurrent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 17081286
 * @date 2019/6/8
 * @since 2019.0624
 */
public class ConcurrentHashMapDemo {


    static Map<String, String> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        //map使用 ConcurrentHashMap 时存和取不会有并发问题，若使用 HashMap 时会有并发问题
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                putMap();
            }).start();
        }

    }

    private static void putMap() {
        for (int j = 0; j < 100; j++) {
            map.put(Thread.currentThread().getName() + j, Thread.currentThread().getName());
        }

        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            System.out.println(next.getKey() + " " + next.getValue());
        }
    }

}
