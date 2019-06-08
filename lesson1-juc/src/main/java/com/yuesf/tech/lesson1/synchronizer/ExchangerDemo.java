package com.yuesf.tech.lesson1.synchronizer;

import java.util.concurrent.Exchanger;

/**
 * @author yuesf
 * @date 19/6/4
 */
public class ExchangerDemo {

    public static void main(String[] args) {
        ExchangerDemo demo = new ExchangerDemo();
        Exchanger exchanger = new Exchanger();

        //必须成对出现
        demo.thread(exchanger, "Thread-1");
        demo.thread(exchanger, "Thread-2");
        //若单着一个线程那么就会阻塞
//        demo.thread(exchanger,"Thread-3");

    }

    /**
     * 线程统用方法
     *
     * @param exchanger
     * @param name
     */
    private void thread(Exchanger exchanger, String name) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 开始交换");
            try {
                Object exchange = exchanger.exchange(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName() + " 交换的数据：" + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, name).start();
    }
}
