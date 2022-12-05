package com.jcip.chapter5;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueDemo {

    public static void main(String[] args) {
        final SynchronousQueue<String> queue = new SynchronousQueue<>();
        new Thread(()->{
            try {
                //queue.put("task");
                final boolean flag = queue.offer("task", 2, TimeUnit.SECONDS);
                System.out.println(flag);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(()->{
            try {
                //System.out.println(queue.take());
                System.out.println(queue.poll(3,TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
