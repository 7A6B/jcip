package com.jcip.chapter6;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTaskDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println("do sth in callable");
            TimeUnit.SECONDS.sleep(3);
            return "hello callable";
        });
        final ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(futureTask);
        System.out.println("在main中执行其他的事情");
        TimeUnit.SECONDS.sleep(1);
        final String res = futureTask.get();
        System.out.println("result:"+res);
        executorService.shutdown();
    }
}
