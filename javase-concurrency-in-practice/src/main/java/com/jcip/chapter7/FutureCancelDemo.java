package com.jcip.chapter7;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class FutureCancelDemo implements Callable<String> {

    private static int counter = 0;
    private final int id = counter++;


    @Override
    public String call() throws Exception {
        System.out.println("----------该任务启动---------");
        TimeUnit.SECONDS.sleep(5);//模拟任务耗时时间为5秒钟
        System.out.println("----------任务完成---------");
        return "success";
    }

    @Override
    public String toString() {
        return "FutureTask{" + "id=" + id + '}';
    }

    public static void main(String[] args) {
        final ExecutorService exec = Executors.newCachedThreadPool();
        final Future<String> future = exec.submit(new FutureCancelDemo());
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        future.cancel(true);
        exec.shutdown();
    }
}
