package com.yuesf.tech.lesson1.atomic;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三种实现并发线程相加操作
 *
 * @author 17081286
 * @date 2019/6/11
 * @since 2019.0624
 */
public class AtomicIntegerDemo {

    int count = 0;

    Lock lock = new ReentrantLock();

    /**
     * 使用 synchronized 加锁
     */
    private synchronized void add() {
        count++;
    }

    /**
     * 使用lock锁
     */
    private void addLock() {
        lock.lock();
        count++;
        lock.unlock();
    }

    @Test
    public void intAdd() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 50000; i++) {
            service.execute(new Thread(() -> {
                add();
            }));
        }
        service.shutdown();
        service.awaitTermination(2000, TimeUnit.MILLISECONDS);
        System.out.println(count);
    }

    /**
     * 使用juc 的 AtomicInteger
     */
    @Test
    public void atomicInteger() {
        AtomicInteger integer = new AtomicInteger();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                integer.incrementAndGet();
            }).start();
        }
        System.out.println(integer.get());
    }

}
