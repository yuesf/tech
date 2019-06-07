package com.yuesf.tech.lesson1.blockingqueue;

import org.junit.Test;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author yuesf
 * @date 19/6/7
 */
public class PriorityBlockingQueueDemo {


    @Test
    public void testPut() throws InterruptedException {
        PriorityBlockingQueue queue = new PriorityBlockingQueue(3);

        queue.put("2");
        queue.put("1");
        queue.put("5");
        queue.put("6");
        queue.put("4");
        queue.put("3");

        System.out.println(queue.size());
        System.out.println(queue.take());
        System.out.println(queue);
    }

    /**
     * 查看执行结果 与其它队列的区别
     * <p>
     * 1.不阻塞，不限制，自排序
     * </p>
     *
     * @param args
     */
    public static void main(String[] args) {
        PriorityBlockingQueue queue = new PriorityBlockingQueue(3);
        BlockingQueueDemo.producer(queue);
        BlockingQueueDemo.consumer(queue);
    }


}
