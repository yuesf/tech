package com.yuesf.tech.lesson1.blockingqueue;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author yuesf
 * @date 19/6/7
 */
public class ArrayBlockingQueueDemo {
    ArrayBlockingQueue queue = new ArrayBlockingQueue(3, false);
//    ArrayBlockingQueue queue = new ArrayBlockingQueue(3);

    /**
     * 如果队列满时，在添加元素那到就会抛出异常
     * <code>java.lang.IllegalStateException: Queue full</code>
     */
    @Test
    public void queueFullException() {
        ArrayBlockingQueue queue = new ArrayBlockingQueue(3, false);
        queue.add("1");
        queue.add("2");
        queue.add("3");
        queue.add("4");
        System.out.println("执行完成");
    }

    public static void main(String[] args) {

        ArrayBlockingQueue queue = new ArrayBlockingQueue(3, false);
        //生产者
        BlockingQueueDemo.producer(queue);

        //消费者
        BlockingQueueDemo.consumer(queue);

    }


}
