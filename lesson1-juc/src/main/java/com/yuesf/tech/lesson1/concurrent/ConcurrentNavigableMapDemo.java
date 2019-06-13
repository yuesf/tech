package com.yuesf.tech.lesson1.concurrent;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.*;

/**
 *线程安全有序Map  ConcurrentSkipListMap
 *
 * @author 17081286
 * @date 2019/6/10
 * @since 2019.0624
 */
public class ConcurrentNavigableMapDemo {

    /**
     * 比较 ConcurrentHashMap 与 ConcurrentSkipListMap 的存储速度，请问谁的速度会快呢？
     *
     * @param args
     */
    public static void main(String[] args) {
        ConcurrentNavigableMapDemo demo = new ConcurrentNavigableMapDemo();
        //ConcurrentSkipListMap 并发执行
        demo.speedSkipListMap();
        //ConcurrentHashMap 并发执行
//        demo.speedHashMap();


    }

    /**
     * 使用 ConcurrentHashMap 测试 put 速度
     */
    public void speedHashMap() {
        long millis = System.currentTimeMillis();
        ConcurrentHashMap hashMap = new ConcurrentHashMap();
        putMapThread(hashMap);
        System.out.println(System.currentTimeMillis() - millis);
    }

    /**
     * 使用 ConcurrentSkipListMap 测试 put 速度
     */
    public void speedSkipListMap() {
        long millis = System.currentTimeMillis();
        ConcurrentSkipListMap hashMap = new ConcurrentSkipListMap();
        putMapThread(hashMap);
        System.out.println(System.currentTimeMillis() - millis);
    }

    private void putMapThread(Map map) {
        CountDownLatch latch = new CountDownLatch(100);
        ExecutorService service =Executors.newFixedThreadPool(5);
        for (int i = 0; i < 100; i++) {
            service.submit(() -> {
                for (int j = 0; j < 100000; j++) {
                    map.put(Thread.currentThread().getName() + j, j);
                }
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }

    /**
     * ConcurrentSkipListMap 有序的一般我们会与 TreeMap 来比较
     * 那么输出结果是什么？
     */
    @Test
    public void headMap() {
        ConcurrentNavigableMap<Integer, Integer> map = new ConcurrentSkipListMap();
        map.put(10, 1);
        map.put(6, 2);
        map.put(9, 3);
        map.put(19, 4);
        map.put(8, 5);
        ConcurrentNavigableMap headMap = map.headMap(10);
        headMap.keySet().stream().forEach(idx -> {
            System.out.println(idx + " value:" + map.get(idx));
        });
        //tailMap
        ConcurrentNavigableMap tailMap = map.tailMap(10);
        tailMap.keySet().stream().forEach(idx -> {
            System.out.println(idx);
        });
        //subMap
        System.out.println("---------subMap----");
        ConcurrentNavigableMap<Integer, Integer> subMap = map.subMap(3, 10);
        subMap.keySet().forEach(idx -> {
            System.out.println(idx);
        });
    }

    /**
     * 请回答输出结果
     */
    @Test
    public void headStringMap() {
        ConcurrentNavigableMap<String, Integer> map = new ConcurrentSkipListMap();
        map.put("abc", 1);
        map.put("bcd", 2);
        map.put("dca", 3);
        map.put("acb", 4);
        map.put("cba", 5);
        map.put("cab", 6);
        ConcurrentNavigableMap headMap = map.headMap("c");
        headMap.keySet().stream().forEach(idx -> {
            System.out.println(idx + " value:" + map.get(idx));
        });
        //
        ConcurrentNavigableMap tailMap = map.tailMap("c");
        tailMap.keySet().stream().forEach(idx -> {
            System.out.println(idx);
        });
    }
}
