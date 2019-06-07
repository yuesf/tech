package com.yuesf.tech.lesson1.blockingqueue;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * <p>与ArrayBlockingQueue 的区别
 * 1.LinkedBlockingQueue 中有两把锁，生产和消费各一把，而 ArrayBlockingQueue 生产和消费使用同一把锁
 * 2.实现机制不同， LinkedBlockingQueue 是链表， ArrayBlockingQueue是数组
 * 3.LinkedBlockingQueue容量可指定，也可不指定，默认Integer.Max_value, ArrayBlockingQueue 是固定容量大小
 * </p>
 *
 * @author yuesf
 * @date 19/6/7
 */
public class LinkedBlockingQueueDemo {

    public static void main(String[] args) {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue(3);

        //生产者
        BlockingQueueDemo.producer(queue);

        //消费者
        BlockingQueueDemo.consumer(queue);
    }


}
