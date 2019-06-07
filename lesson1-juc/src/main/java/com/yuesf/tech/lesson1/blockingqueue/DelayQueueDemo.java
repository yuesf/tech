package com.yuesf.tech.lesson1.blockingqueue;

import com.yuesf.tech.lesson1.ThreadTools;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author yuesf
 * @date 19/6/7
 */
public class DelayQueueDemo {

    public static void main(String[] args) {
        DelayQueue<Message> queue = new DelayQueue();

        Message message1 = new Message("h", 1000);
        Message message2 = new Message("e", 4000);
        queue.offer(message2);
        queue.offer(message1);

        consumer(queue);
    }

    /**
     * 消费实现的多种方式
     * 1.take
     * 2.poll
     * 3.remove
     *
     * @param queue
     */
    private static void consumer(DelayQueue<Message> queue) {
        new Thread(() -> {
            while (true) {
//                Message peek = queue.peek();
//                if(null == peek){
//                    return;
//                }

                try {
                    Message message = queue.remove();
                    System.out.println("消费:" + message.body);
                } catch (Exception e) {
                    System.out.println("等待生产者");
                    ThreadTools.sleep(2000);
                }

            }
        }).start();
    }

    private static class Message implements Delayed {

        private String body;
        private long excuteTime;

        public Message(String body, long delayTime) {
            this.body = body;
            this.excuteTime = TimeUnit.NANOSECONDS.convert(delayTime, TimeUnit.MILLISECONDS) + System.nanoTime();
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.excuteTime - System.nanoTime(), TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed delayed) {
            Message msg = (Message) delayed;
            return this.body.compareTo(msg.body);
//            return Integer.valueOf(this.id) > Integer.valueOf(msg.id) ? 1
//                    : (Integer.valueOf(this.id) < Integer.valueOf(msg.id) ? -1 : 0);
        }
    }


}
