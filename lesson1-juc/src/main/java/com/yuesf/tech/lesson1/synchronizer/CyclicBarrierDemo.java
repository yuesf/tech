package com.yuesf.tech.lesson1.synchronizer;

import com.yuesf.tech.lesson1.ThreadTools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author yuesf
 * @date 19/6/4
 */
public class CyclicBarrierDemo {
    /**
     * 回顾与 CountDownLatch 的区别？
     *
     * @param args
     */
    public static void main(String[] args) {
        CyclicBarrierDemo demo = new CyclicBarrierDemo();

        demo.demo();
    }

    /**
     * 计数值 >0时阻塞
     *
     * <p>
     * 示例：流水客车，满坐才发车，若未满则一直等待，只有掐好满坐发车后，也不会有乘客则结束
     * </p>
     */
    public void demo() {
        //每次屏障结束后都会执行构造线程
        CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            System.out.println(Thread.currentThread().getName() + " ---- 当前线程1");
            ThreadTools.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " ---- 当前线程2");
        });
        //数量是2的倍数时或不倍数时执行结果是不一样的
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 线程执行");
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 线程执行完成");

            }).start();
        }

        System.out.println("所有执行完成");

    }

}
