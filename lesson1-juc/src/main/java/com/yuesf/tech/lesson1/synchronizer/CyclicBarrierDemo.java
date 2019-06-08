package com.yuesf.tech.lesson1.synchronizer;

import com.yuesf.tech.lesson1.ThreadTools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author yuesf
 * @date 19/6/4
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
//        new CyclicBarrierDemo().demo1();
        new CyclicBarrierDemo().demo();
    }

    /**
     * 计数值 >0时阻塞
     */
    public void demo() {
        //每次屏障结束后都会执行构造线程
        CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            System.out.println(Thread.currentThread().getName() + " ---- 当前线程1");
            ThreadTools.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " ---- 当前线程2");
        });
        for (int i = 0; i < 3; i++) {
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




    public void demo1() {
        //构造器：设置屏障放开前做的事情
        CyclicBarrier barrier3 = new CyclicBarrier(2, () -> {
            System.out.println("屏障放开，[屏障线程]先运行！");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[屏障线程]的事情做完了!");
        });
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
//                ThreadTools.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " 等待屏障放开");
                try {
                    barrier3.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "开始干活...干活结束");
            }).start();
        }
    }

}
