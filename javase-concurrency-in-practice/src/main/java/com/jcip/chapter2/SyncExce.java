package com.jcip.chapter2;

import java.util.concurrent.TimeUnit;

public class SyncExce {
    public synchronized void foo(int i){
        try{
            System.out.println("执行foo("+i+")执行开始");
            if(i ==3){
                throw new RuntimeException("参数异常");
            }
            TimeUnit.SECONDS.sleep(3);
            System.out.println("执行foo("+i+")执行结束");
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final SyncExce syncExce = new SyncExce();
        new Thread(()->{
            syncExce.foo(3);
        }).start();
        new Thread(()->{
            syncExce.foo(5);
        }).start();
    }
}