package com.jcip.chapter4;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PriorityueueThreadDemo {

    public static void main(String[] args) {
        final PriorityBlockingQueue<String> blockingQueue = new PriorityBlockingQueue<>();
        new Thread(()->{
            while (!Thread.interrupted()) {
                try {
                    System.out.println("take=>" + blockingQueue.take());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                blockingQueue.put("order-"+i);
                try {
                    TimeUnit.SECONDS.sleep(2);
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
