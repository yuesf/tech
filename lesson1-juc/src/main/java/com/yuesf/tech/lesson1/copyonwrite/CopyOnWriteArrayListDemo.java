package com.yuesf.tech.lesson1.copyonwrite;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yuesf
 * @date 19/6/7
 */
public class CopyOnWriteArrayListDemo {
    //当使用CopyOnWriteArrayList 稳定输出
    List<String> list = new CopyOnWriteArrayList<>();
    //当使用ArrayList时会抛异常
//    List<String> list = new ArrayList<>();

    public static void main(String[] args) {

        CopyOnWriteArrayListDemo demo = new CopyOnWriteArrayListDemo();
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            service.execute(()->{
                //我们经常在一个类中写两个方法 一个写一个读。 那么list放在全局变量时就要考虑多线程的场景问题
                demo.write();

                demo.read();
            });
        }


        service.shutdown();
    }

    private  void write() {
        for (int j = 0; j < 3; j++) {
            list.add(j+"");
        }
    }
    private void read() {
        for (String str : list) {
            System.out.println(Thread.currentThread().getName() + ": " + str);
        }
    }
}
