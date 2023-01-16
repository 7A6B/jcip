package com.jcip.chapter8;

import java.util.concurrent.TimeUnit;

public class ThreadPoolTask implements Runnable{
    private static int counter = 0;
    private final int id = counter++;

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("id=>"+id+"的任务运行结束  "+Thread.currentThread().getName());
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}
