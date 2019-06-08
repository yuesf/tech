package com.yuesf.tech.lesson1.blockingqueue;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 与 LinkedTransferQueue  的区别 在于 LinkedBlockingDeque 是双向链表，可对队列双向操作，而 LinkedTransferQueue 是单向链表
 * @author 17081286
 * @date 2019/6/8
 * @since 2019.0624
 */
public class LinkedBlockingDequeDemo {

    public static void main(String[] args) {
        LinkedBlockingDeque deque = new LinkedBlockingDeque(3);

        BlockingQueueDemo.producer(deque);
        BlockingQueueDemo.consumer(deque);
    }
}
