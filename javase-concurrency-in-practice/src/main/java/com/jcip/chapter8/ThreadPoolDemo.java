package com.jcip.chapter8;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {

    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10,
                               1L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

        poolExecutor.prestartAllCoreThreads();
        System.in.read();
    }
}
