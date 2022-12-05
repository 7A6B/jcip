package com.jcip.chapter3;


import java.util.concurrent.TimeUnit;

class Hoo{
    String str ="hoo";
}
public class ThreadLocalApp2 {

    public static void main(String[] args) {
        final Hoo hoo = new Hoo();
        final ThreadLocal<Hoo> hooThreadLocal = new ThreadLocal<>();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("111111==>"+hooThreadLocal.get());
        }).start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            hoo.str= "updateFoo";
            hooThreadLocal.set(hoo);
            System.out.println("222222==>"+hooThreadLocal.get().str);
        }).start();
    }
}
