package com.yuesf.tech.lesson1.concurrent;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 17081286
 * @date 2019/6/8
 * @since 2019.0624
 */
public class ConcurrentHashMapDemo {

    public static void main(String[] args) throws InterruptedException {


    }

    @Test
    public  void atomic() throws InterruptedException {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        map.put("key", 1);

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        AtomicInteger integer = new AtomicInteger();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(()->{
                int value = integer.incrementAndGet();
                map.put("key", value);
                System.out.println(value);
            });
        }
        Thread.sleep(3000); //模拟等待执行结束
        System.out.println("------" + map.get("key") + "------");
        executorService.shutdown();
    }

    @Test
    public  void mapGet() throws InterruptedException {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        map.put("key", 1);

        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 1000; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    int key = map.get("key") + 1;
                    map.put("key", key);
                    System.out.println(key);
                }
            });
        }
        Thread.sleep(3000); //模拟等待执行结束
        System.out.println("------" + map.get("key") + "------");
        executorService.shutdown();
    }

}
