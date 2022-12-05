package com.jcip.chapter4;

import java.util.concurrent.TimeUnit;

public class PrivateLock {
    private final Object myLock = new Object();
    PrivateLock(){}
    void someMethod(){
        synchronized (myLock){
            System.out.println("模拟输出------------------");
        }
    }

    public Object getMyLock() {
        return myLock;
    }

    public static void main(String[] args) {
        final PrivateLock privateLock = new PrivateLock();
        new Thread(()->{
            //通过公有的方法来访问锁
            final Object lock = privateLock.getMyLock();
            synchronized (lock){//和 synchronized (myLock) 是一把锁
                try{
                    TimeUnit.SECONDS.sleep(3);
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
            }
        }).start();
        new Thread(privateLock::someMethod).start();
    }
}

