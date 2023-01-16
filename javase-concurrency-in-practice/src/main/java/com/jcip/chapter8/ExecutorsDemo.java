package com.jcip.chapter8;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsDemo {

    static void testFixedThreadPool(){
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(()->{
                System.out.println("----------");
            });
        }
    }

    static void testCachedThreadPool(){
        final ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            exec.execute(()->{
                System.out.println(Thread.currentThread().getName()+"-----------");
            });
        }
    }
    public static void main(String[] args) throws IOException {
        //testFixedThreadPool();
        testCachedThreadPool();
        System.in.read();
    }
}
