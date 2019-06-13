package com.yuesf.tech.lesson1.copyonwrite;

import org.junit.Test;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CopyOnWriteArraySet 是由 CopyOnWriteArrayList 实现
 *
 * @author yuesf
 * @date 19/6/7
 */
public class CopyOnWriteArraySetDemo {
    //与CopyOnWriteArrayListDemo的示例中只修改把List变成Set那么请查看输出结果，查看变化
    Set<String> set = new CopyOnWriteArraySet<>();
    CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    /**
     * 通过 CopyOnWriteArraySet 实现并发线程
     */
    @Test
    public void setDemo() {

        CopyOnWriteArraySetDemo demo = new CopyOnWriteArraySetDemo();
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            service.execute(() -> {
                //我们经常在一个类中写两个方法 一个写一个读。 那么list放在全局变量时就要考虑多线程的场景问题
                demo.write();

                demo.read();
            });
        }
        service.shutdown();
    }

    /**
     * 通过 CopyOnWriteArrayList 实现 CopyOnWriteArraySet
     */
    @Test
    public void listDemo() {
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            service.execute(() -> {
                //我们经常在一个类中写两个方法 一个写一个读。 那么list放在全局变量时就要考虑多线程的场景问题
                writeListBySet();

                readListBySet();
            });
        }
        service.shutdown();

    }


    /**
     * CopyOnWriteArraySet 的写操作
     */
    private void write() {
        for (int j = 0; j < 3; j++) {
            set.add(j + "");
        }
    }

    /**
     * CopyOnWriteArraySet的读操作
     */
    private void read() {
        for (String str : set) {
            System.out.println(Thread.currentThread().getName() + ": " + str);
        }
    }

    /**
     * 通过CopyOnWriteArrayList实现CopyOnWriteArraySet 的写操作
     */
    private void writeListBySet() {
        for (int j = 0; j < 3; j++) {
            list.addIfAbsent(j + "");
        }
    }

    /**
     * 通过CopyOnWriteArrayList实现CopyOnWriteArraySet的读操作
     */
    private void readListBySet() {
        for (String str : list) {
            System.out.println(Thread.currentThread().getName() + ": " + str);
        }
    }
}
