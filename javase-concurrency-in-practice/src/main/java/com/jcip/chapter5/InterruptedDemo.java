package com.jcip.chapter5;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class InterruptedDemo {

    public static void main(String[] args) {
        final ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        final Thread thread = new Thread(() -> {
            try {
                System.out.println("从有界队列中获取元素==>" + blockingQueue.take());
            } catch (InterruptedException e) {
                System.err.println("发生异常之后改线程的状态为==>"+Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt();
                System.err.println("发生异常之后改线程的状态为==>"+Thread.currentThread().isInterrupted());
            }
        });
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread.interrupt();
    }
}
