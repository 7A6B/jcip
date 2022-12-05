package com.jcip.chapter2;

import java.util.concurrent.TimeUnit;

public class Widget {
    public synchronized void doSth(){
        System.out.println(Thread.currentThread().getName()+"==>"+"进入 父类的doSth");
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName()+"==>"+"结束 父类的doSth");
    }
}
