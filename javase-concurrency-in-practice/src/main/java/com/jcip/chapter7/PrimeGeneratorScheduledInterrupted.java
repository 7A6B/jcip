package com.jcip.chapter7;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PrimeGeneratorScheduledInterrupted {
    private static final ScheduledExecutorService cancelExec = new ScheduledThreadPoolExecutor(1);
    static void timedRun(Runnable runnable,long timeOut, TimeUnit unit){
        final Thread taskThread = Thread.currentThread();
        cancelExec.schedule(taskThread::interrupt,timeOut,unit);
        runnable.run();
    }
    static void calc(){
        timedRun(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            }catch (InterruptedException e){
                throw new RuntimeException();
            }
            System.out.println("任务在0.5秒之内完成");
        },800,TimeUnit.MILLISECONDS);
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("在调用timedRun()之后，模拟执行1秒的耗时任务");
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    static void calc2(){
        timedRun(()->{
            InputStream in = System.in;
            try{
                System.out.println("执行一个不响应中断的任务——请输入内容：");
                in.read();
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        },800,TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) {
        //calc();
        calc2();
    }
}
