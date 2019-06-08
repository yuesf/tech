package com.yuesf.tech.lesson1.blockingqueue;

import com.yuesf.tech.lesson1.ThreadTools;

import java.util.concurrent.LinkedTransferQueue;

/**
 * 无阻塞的队列
 * <p>
 * 在生产时 是指 put offer add 时无阻塞，而 tranfer时阻塞
 * 在消费时是指 remove poll 时无阻塞，而 take时阻塞
 * </P>
 *
 * @author 17081286
 * @date 2019/6/8
 * @since 2019.0624
 */
public class LinkedTransferQueueDemo {

    public static void main(String[] args) {
        LinkedTransferQueue queue = new LinkedTransferQueue();

        //单独执行put时 无阻塞
        BlockingQueueDemo.producer(queue);
        //        //使用transfer 时阻塞
        //        producer(queue);
        //        //使用remove时 队列为空时异常
        //        BlockingQueueDemo.consumer(queue);
        //        //使用take时阻塞   poll时消费为空时返回null
        //        consumer(queue);
    }

    /**
     * @param queue
     */
    private static void consumer(LinkedTransferQueue queue) {
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

    /**
     * transfer 时同步
     * put offer add 时异步
     *
     * @param queue
     */
    private static void producer(LinkedTransferQueue queue) {

        new Thread(() -> {
            while (true) {
                long random = BlockingQueueDemo.random();
                try {
                    System.out.println(Thread.currentThread().getName() + "开始生产：" + random);
                    queue.transfer(random);
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
