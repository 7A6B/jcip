package com.jcip.chapter6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AwaitTerminationDemo {

    public static void main(String[] args) throws InterruptedException {
        final ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(()->{
            System.out.println(Thread.currentThread().getName()+" : "+System.currentTimeMillis());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName()+" : "+System.currentTimeMillis());
        });
        //executor.shutdown();
        System.out.println(Thread.currentThread().getName()+" ---------"+System.currentTimeMillis());
        System.out.println(executor.awaitTermination(5,TimeUnit.SECONDS));
        System.out.println(Thread.currentThread().getName()+" =========="+System.currentTimeMillis());
    }
}
