package com.yuesf.tech.lesson1.atomic;

import com.yuesf.tech.lesson1.ThreadTools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 17081286
 * @date 2019/6/13
 * @since 2019.0624
 */
public class AtomicReferenceDemo {


    public static void main(String[] args) {

        User user = new User();
        user.setId("1001");
        user.setName("yuesf");
        user.setAge(1);
        AtomicReference<User> updater = new AtomicReference(user);

        ExecutorService service = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 100; i++) {
            service.submit(() -> {
                User userUpdater = updater.get();
                userUpdater.setAge(userUpdater.getAge() + 1);
            });
        }

        User user1 = new User();
        user1.setId("1002");
        user1.setName("hello");
//                updater.compareAndSet(user,user1);
        service.shutdown();
//        ThreadTools.sleep(2000);
        System.out.println(updater.get().getAge());

    }

    static class User {
        private String id;
        public volatile String name;
        private Integer age;

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
