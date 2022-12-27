package com.jcip.chapter6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class TaskNow implements Runnable{
    private static int counter = 0;
    private final int id = counter++;

    @Override
    public void run() {
        try {
            System.out.println("taskNow->" + id + " 开始执行");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("taskNow->" + id + " 执行结束");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

public class ShutdownNowDemo {

    public static void main(String[] args) throws InterruptedException {
        final ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new TaskNow());
        executor.execute(new TaskNow());
        executor.execute(new TaskNow());//从该任务开始 后续未执行地任务会被清除调
        executor.execute(new TaskNow());
        executor.execute(new TaskNow());
        TimeUnit.SECONDS.sleep(1);
        executor.shutdownNow();
        executor.execute(new TaskNow());//关闭以后再提交任务 将会报错
    }
}
