package com.jcip.chapter7;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BlockedMutex{
    private final Lock lock = new ReentrantLock();
    //在构造该对象的时候即加锁
    public BlockedMutex(){
        lock.lock();
    }
    public void f(){
        try {
            lock.lockInterruptibly();//在此是无法获取锁的
            System.out.println("在f()中等待获取锁......");
        } catch (InterruptedException e) {
            System.out.println("从f()中获取锁中断的异常处理--------");
        }
    }
}
class BlockedTask implements Runnable{
    BlockedMutex blocked= new BlockedMutex();

    @Override
    public void run() {
        System.out.println("在BlockedMutex f()中等待获取锁");
        blocked.f();//在此阻塞
        System.out.println("在BlockedMutex f()中断处理之后，继续执行任务的处理");
    }
}
public class LockInterruptiblyDemo {

    public static void main(String[] args) throws InterruptedException {
        final Thread thread = new Thread(new BlockedTask());
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("2秒后触发中断");
        thread.interrupt();
    }
}
