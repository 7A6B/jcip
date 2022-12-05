package com.jcip.chapter5;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {

    public static void main(String[] args) {
        final BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        new Thread(()->{
            try {
                System.out.println("从有界阻塞队列中获取元素==>"+blockingQueue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                blockingQueue.put("order-1");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
