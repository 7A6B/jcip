package com.jcip.chapter5;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class SleepBlocked implements Runnable{

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            //线程被中断后，在处理异常之后，中断状态会恢复
            System.out.println("打断睡眠线程执行之后该线程的状态为==>"+Thread.currentThread().isInterrupted());
        }
        System.out.println("退出睡眠的执行...");
    }
}
//note-> 此处的IO操作不会被中断
class IOBlocked implements Runnable{

    private InputStream in;
    public IOBlocked(InputStream is){
        in = is;
    }

    @Override
    public void run() {
        try {
            System.out.println("等待读取信息");
            in.read();
        }catch (IOException e){
            if(Thread.currentThread().isInterrupted()){
                System.out.println("Interrupted from blocked IO");
            }else{
                throw new RuntimeException(e);
            }
        }
        System.out.println("退出IOBlocked");
    }
}
//note-> synchronized上的等待不会中断
class SynchronizedBlocked implements Runnable{

    public synchronized void f(){
        while (true){
            Thread.yield();
        }
    }
    public SynchronizedBlocked(){
        new Thread(this::f).start();
    }
    @Override
    public void run() {
        System.out.println("试着去调用f()");
        f();
        System.out.println("退出SynchronizedBlocked的执行");
    }
}

public class Interrupting {
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    static void test(Runnable r) throws InterruptedException {
        final Future<?> future = executorService.submit(r);
        TimeUnit.SECONDS.sleep(2);
        System.out.println("将要去打断的类为==>"+r.getClass().getName());
        future.cancel(true);//打断正在执行的任务
        System.out.println("打断信号已经发送给了==>"+r.getClass().getName());
    }

    public static void main(String[] args)throws Exception {
        //test(new SleepBlocked());
        //test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());
    }
}
