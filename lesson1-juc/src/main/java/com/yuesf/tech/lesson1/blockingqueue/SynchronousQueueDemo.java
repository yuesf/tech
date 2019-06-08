package com.yuesf.tech.lesson1.blockingqueue;

import com.yuesf.tech.lesson1.ThreadTools;

import java.util.concurrent.SynchronousQueue;

/**
 * @author 17081286
 * @date 2019/6/8
 * @since 2019.0624
 */
public class SynchronousQueueDemo {

    public static void main(String[] args) {
        SynchronousQueue queue = new SynchronousQueue();

        //        //只有一个生产者的时候 使用put是阻塞的
        //        producer(queue);
        //        //只有一个消费者的时候 使用take是阻塞的
        //        consumer(queue);
        //        //使用一个 生产者一个消费者时， 类似于线程之间数据交换
        //        producer(queue);
        //        consumer(queue);
        //        //多个生产者 一个消费者时，未被消费的生产者阻塞
        //        producer(queue);
        //        producer(queue);
        //        consumer(queue);
        //        //一个生产者多个消费者时，未有消费数据的消费者阻塞
        //        producer(queue);
        //        consumer(queue);
        //        consumer(queue);
        //多个生产者多个消费者时，两者交替阻塞
        producer(queue);
        producer(queue);
        consumer(queue);
        consumer(queue);

        /*
         *  通过以上比较总结 与 Exchanger 的区别是什么？
         */
    }

    private static void consumer(SynchronousQueue queue) {
        new Thread(() -> {
            while (true) {
                long random = BlockingQueueDemo.random();
                try {
                    System.out.println(Thread.currentThread().getName() + "开始消费");
                    Object take = queue.take();
                    System.out.println(Thread.currentThread().getName() + "已消费：" + take);
                    ThreadTools.sleep(random);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void producer(SynchronousQueue queue) {

        new Thread(() -> {
            while (true) {
                long random = BlockingQueueDemo.random();
                try {
                    System.out.println(Thread.currentThread().getName() + "开始生产：" + random);
                    queue.put(random);
                    System.out.println(Thread.currentThread().getName() + "生产完成：" + random);
                    ThreadTools.sleep(random);
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + "队列已满，等待消费");
                    ThreadTools.sleep(2000);
                }
            }
        }).start();
    }

}
