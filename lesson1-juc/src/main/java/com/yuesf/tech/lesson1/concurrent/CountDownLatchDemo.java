package com.yuesf.tech.lesson1.concurrent;

import com.yuesf.tech.lesson1.ThreadTools;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * CountDownLatch示例
 */
public class CountDownLatchDemo {


    /**
     * 示例CountDownLatch
     *
     * @throws InterruptedException
     */
    @Test
    public void countdown() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            int idx = i;
            new Thread(() -> {
                action(idx);
                latch.countDown();
            }).start();
        }
        latch.await();
        System.out.println("全部执行完成");

    }


    /**
     * java5之前实现 CountDownLatch
     */
    @Test
    public void before5() throws InterruptedException {

        CountDownBefore5 latch = new CountDownBefore5(3);
        for (int i = 0; i < 3; i++) {
            int idx = i;
            new Thread(() -> {
                action(idx);
                latch.countDown();
            }).start();
        }


        latch.await();
        System.out.println("全部执行完成");
    }


    /**
     * 使用java5实现CountDownLatch
     */
    @Test
    public void java5() throws InterruptedException {
        CountDownJava5 latch = new CountDownJava5(3);

        for (int i = 0; i < 3; i++) {
            int idx = i;
            new Thread(() -> {
                action(idx);
                latch.countDown();
            }).start();
        }


        latch.await();
        System.out.println("全部执行完成");
    }

    private void action(int idx) {
        System.out.println(Thread.currentThread() + "开始执行");
        ThreadTools.sleep(1000 * (idx + 1));
        System.out.println(Thread.currentThread() + "执行结束");
    }

    /**
     * 使用java5之后的lock锁实现CountDownLatch
     */
    private class CountDownJava5 {
        private long count;

        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        private CountDownJava5(long count) {
            this.count = count;
        }

        /**
         * 使用lock实现锁
         */
        public void countDown() {
            lock.lock();
            --count;
            if (count <= 0) {
                count = 0;
                condition.signalAll();
            }
            lock.unlock();

        }

        public void await() throws InterruptedException {
            lock.lock();
            try {
                while (count > 0) {
                    condition.await();
                }
            } finally {
                lock.unlock();
            }

        }

    }

    /**
     * java5之前实现CountDownLatch
     */
    private class CountDownBefore5 {
        private long count;

        public CountDownBefore5(long count) {
            this.count = count;
        }


        /**
         * 计算减1
         */
        public void countDown() {

            synchronized (this) {
                --count;
                if (count <= 0) {
                    count = 0;
                    notifyAll();
                }

            }
        }

        /**
         * 阻塞
         *
         * @throws InterruptedException
         */
        public void await() throws InterruptedException {
            synchronized (this) {
                while (count > 0) {
                    wait();
                }
            }
        }
    }


}
