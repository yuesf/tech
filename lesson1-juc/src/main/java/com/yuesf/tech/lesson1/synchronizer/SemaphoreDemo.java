package com.yuesf.tech.lesson1.synchronizer;

import com.yuesf.tech.lesson1.ThreadTools;

import java.util.concurrent.Semaphore;

/**
 * @author yuesf
 * @date 19/6/4
 */
public class SemaphoreDemo {

    /**
     * 号称：信号量
     * <p>
     *     示例：停车场位置，车位满了则等待，当车位空余才可进入停车场。所有车子离开停车场，则结束
     * </p>
     * 回顾与 CyclicBarrier 的区别
     *
     * @param args
     */
    public static void main(String[] args) {
        //同一时刻只能有定量的数量进入
        Semaphore semaphore = new Semaphore(2);

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 开始");
                ThreadTools.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " 结束");
                semaphore.release(); //被释放后其它线程才可进入，所以使用完一定要释放掉
            }).start();
        }

    }
}
