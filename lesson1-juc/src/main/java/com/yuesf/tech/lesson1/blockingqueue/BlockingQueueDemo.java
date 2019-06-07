package com.yuesf.tech.lesson1.blockingqueue;

import com.yuesf.tech.lesson1.ThreadTools;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * @author yuesf
 * @date 19/6/7
 */
public class BlockingQueueDemo {

    /**
     * 消费者
     */
    public static void consumer(BlockingQueue queue) {
        new Thread(() -> {
            while (true) {
                ThreadTools.sleep(random());
                try {
                    //消费的方式有多种，需要根据需求定义 自己的消费方式，消费方式有以下几种
                    // poll
                    // take
                    // remove
                    Object remove = queue.remove();
                    System.out.println("\n消费: " + remove + " ");
                } catch (Exception e) {
                    System.out.println("当队列为空时异常，需要等待生产");
                    ThreadTools.sleep(1000);
                }
            }
        }).start();
    }

    /**
     * 生产者
     */
    public static void producer(BlockingQueue queue) {
        new Thread(() -> {
            for (int i = 0; ; i++) {

                ThreadTools.sleep(random());
                try {
                    System.out.print("\n");
                    queue.iterator().forEachRemaining(str -> System.out.print(str + " "));
                    //生产时需要考虑生产方式，生产方式有以下几种
                    // add
                    // offer
                    // out
                    queue.add(i + "");
                    System.out.print(" --生产: " + i+"\n");
                } catch (Exception e) {
                    System.out.println("队列满时抛出异常，并等待消费者消费");
                    ThreadTools.sleep(2000);
                }
            }

        }).start();
    }

    public static long random() {
        Random random = new Random();
        long nextLong = random.nextInt(1000);
        return nextLong;
    }
}
