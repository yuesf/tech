package com.yuesf.tech.lesson1.synchronizer;

import com.yuesf.tech.lesson1.ThreadTools;

import java.util.concurrent.Semaphore;

/**
 * @author yuesf
 * @date 19/6/4
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        //同一时刻只能有定量的数量进入
        Semaphore semaphore = new Semaphore(2);


        for (int i = 0; i < 3; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" 开始");
                ThreadTools.sleep(1000);
                System.out.println(Thread.currentThread().getName()+" 结束");
                semaphore.release(); //被释放后其它线程才可进入，所以使用完一定要释放掉
            }).start();
        }

    }
}
