package com.jcip.chapter6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Task implements Runnable{
    private static int counter = 0;
    private final int id = counter++;

    @Override
    public void run() {
        try {
            System.out.println("task->" + id + " 开始执行");
            TimeUnit.SECONDS.sleep(3);
            System.out.println("task->" + id + " 执行结束");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
public class ShutdownDemo {

    public static void main(String[] args) {
        final ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new Task());
        executor.execute(new Task());
        executor.shutdown();//平缓地关闭 是否会等待提交地任务完成？
        executor.execute(new Task());
        System.out.println("main...........");
    }
}
