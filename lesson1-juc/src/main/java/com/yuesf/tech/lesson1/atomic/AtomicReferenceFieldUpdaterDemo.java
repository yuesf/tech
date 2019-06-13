package com.yuesf.tech.lesson1.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author 17081286
 * @date 2019/6/13
 * @since 2019.0624
 */
public class AtomicReferenceFieldUpdaterDemo {

    /**对原子对象的更新字段，但字段必须是 volatile 类型的，也就是支持线程可见性
     * @param args
     */
    public static void main(String[] args) {
        AtomicReferenceFieldUpdater updater = AtomicReferenceFieldUpdater.newUpdater(AtomicReferenceDemo.User.class,String.class,"name");

        AtomicReferenceDemo.User user = new AtomicReferenceDemo.User();
        user.setId("1");
        updater.compareAndSet(user,user.name,"yuesf");
        System.out.println(user.getId());

    }


}
