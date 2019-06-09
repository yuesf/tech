package com.yuesf.tech.lesson1.concurrent;

import com.yuesf.tech.lesson1.ThreadTools;
import com.yuesf.tech.lesson1.blockingqueue.BlockingQueueDemo;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 非阻塞队列
 *
 * @author yuesf
 * @date 19/6/9
 */
public class ConcurrentLinkedQueueDemo {

    public static void main(String[] args) {
        ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();


        producer(queue);

        consumer(queue);
        consumer(queue);
    }

    private static void producer(ConcurrentLinkedQueue queue) {
        new Thread(() -> {
            for (int i = 0; ; i++) {
                ThreadTools.sleep(BlockingQueueDemo.random());
                try {
                    queue.add(i + "");
                    System.out.println(Thread.currentThread().getName() + "生产：" + i);

                } catch (Exception e) {
                    System.out.println("生产异常");
                    ThreadTools.sleep(BlockingQueueDemo.random());
                }
            }
        }).start();
    }

    private static void consumer(ConcurrentLinkedQueue queue) {
     new Thread(()->{
         while (true) {
             ThreadTools.sleep(BlockingQueueDemo.random());

             System.out.print("\n队列元素：");
             queue.stream().forEach(str->{
                 System.out.print(str+" ");
             });
             System.out.println();
             try {
                 Object remove = queue.remove();
                 System.out.println(Thread.currentThread().getName() + " 消费：" + remove);

             } catch (Exception e) {
                 System.out.println("队列为空，等待生产");
                 ThreadTools.sleep(BlockingQueueDemo.random());

             }
         }
     }).start();

    }
}
