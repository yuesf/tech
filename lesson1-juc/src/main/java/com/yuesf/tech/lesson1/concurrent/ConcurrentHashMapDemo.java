package com.yuesf.tech.lesson1.concurrent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * 比较 ConcurrentHashMap 与 HashMap 的使用场景，注意并发场景
 * <p>
 * 经常会与 Vector、 HashTable、 HashMap、 ConcurrentHashMap 的比较。
 * 扩展一下， 普通map 如何转化为线程安全的Map
 * </p>
 *
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

    /**
     * 普通Map转化线程安全的Map的方式
     */
    public void changeConcurrentMap() {
        Map map = new HashMap();

        //1.通过构造转化
        ConcurrentHashMap hashMap = new ConcurrentHashMap<>(map);
        //2.通过 Collections 提供的方法转化
        Map synchronizedMap = Collections.synchronizedMap(map);

    }

}
