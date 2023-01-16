package com.jcip.chapter8;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10,
                                                                   1L, TimeUnit.SECONDS,
                                                                   new SynchronousQueue<>());
        for (int i = 0; i < 100; i++) {
            executor.execute(new ThreadPoolTask());
            TimeUnit.MILLISECONDS.sleep(100);
        }

    }
}
