package com.jcip.chapter7;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

class ExceptionThread2 implements Runnable{

    @Override
    public void run() {
        final Thread thread = Thread.currentThread();
        System.out.println("4, run() by "+thread.getName());
        System.out.println("5, eh = "+thread.getUncaughtExceptionHandler());
        throw new RuntimeException("aha");
    }
}
class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("6, 捕获了 "+e);
    }
}
class HandlerThreadFactory implements ThreadFactory{

    @Override
    public Thread newThread(Runnable r) {
        System.out.println("1, 由 "+ this + " 创建新线程");
        final Thread t = new Thread(r);
        System.out.println("2, 创建好了 "+ t +" 线程");
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        System.out.println( "3, eh = " + t.getUncaughtExceptionHandler());
        return t;
    }

    @Override
    public String toString() {
        return "HandlerThreadFactory";
    }
}
public class CaptureUncaughtException {

    public static void main(String[] args) {
        final ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
        final Future<?> future = exec.submit(new ExceptionThread2());
        try {
            final Object o = future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("捕获了异常=>"+e.getClass().getSimpleName()+" Caused by->"+e.getCause());
        }
        exec.shutdown();
    }
}
