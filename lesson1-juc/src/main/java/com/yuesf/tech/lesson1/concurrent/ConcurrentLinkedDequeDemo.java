package com.yuesf.tech.lesson1.concurrent;

import com.yuesf.tech.lesson1.ThreadTools;
import com.yuesf.tech.lesson1.blockingqueue.BlockingQueueDemo;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 双向链表的非阻塞队列
 *
 * @author 17081286
 * @date 2019/6/10
 * @since 2019.0624
 */
public class ConcurrentLinkedDequeDemo {

    public static void main(String[] args) {
        ConcurrentLinkedDeque deque = new ConcurrentLinkedDeque();

        //队列头和尾可同时操作
        producerFirst(deque);
        consumerFirst(deque);
        producerLast(deque);
        consumerLast(deque);

    }

    private static void producerFirst(ConcurrentLinkedDeque deque) {
        new Thread(() -> {
            for (int i = 0; ; i++) {
                ThreadTools.sleep(BlockingQueueDemo.random());
                try {
                    deque.addFirst(i + "");
                    System.out.println(Thread.currentThread().getName() + "生产头：" + i);

                } catch (Exception e) {
                    System.out.println("生产异常");
                    ThreadTools.sleep(BlockingQueueDemo.random());
                }
            }
        }).start();
    }

    private static void producerLast(ConcurrentLinkedDeque deque) {
        new Thread(() -> {
            for (int i = 0; ; i++) {
                ThreadTools.sleep(BlockingQueueDemo.random());
                try {
                    deque.addLast(i + "");
                    System.out.println(Thread.currentThread().getName() + "生产尾：" + i);

                } catch (Exception e) {
                    System.out.println("生产异常");
                    ThreadTools.sleep(BlockingQueueDemo.random());
                }
            }
        }).start();
    }

    private static void consumerFirst(ConcurrentLinkedDeque deque) {
        new Thread(() -> {
            while (true) {
                ThreadTools.sleep(BlockingQueueDemo.random());

                System.out.print("\n队列元素：");
                deque.stream().forEach(str -> {
                    System.out.print(str + " ");
                });
                System.out.println();
                try {
                    Object remove = deque.removeFirst();
                    System.out.println(Thread.currentThread().getName() + " 消费头：" + remove);

                } catch (Exception e) {
                    System.out.println("队列为空，等待生产");
                    ThreadTools.sleep(BlockingQueueDemo.random());

                }
            }
        }).start();
    }

    private static void consumerLast(ConcurrentLinkedDeque deque) {
        new Thread(() -> {
            while (true) {
                ThreadTools.sleep(BlockingQueueDemo.random());

                System.out.print("\n队列元素：");
                deque.stream().forEach(str -> {
                    System.out.print(str + " ");
                });
                System.out.println();
                try {
                    Object remove = deque.removeLast();
                    System.out.println(Thread.currentThread().getName() + " 消费尾：" + remove);

                } catch (Exception e) {
                    System.out.println("队列为空，等待生产");
                    ThreadTools.sleep(BlockingQueueDemo.random());

                }
            }
        }).start();
    }
}
