package com.jcip.chapter2;

import java.util.concurrent.TimeUnit;

public class SyncClass {
    public  synchronized static void f(){
        try {
            System.out.println("f()..........begin");
            TimeUnit.SECONDS.sleep(3);
            System.out.println("f()...........end");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void g() {
        synchronized (SyncClass.class) {
            try {
                System.out.println("g()..........begin");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("g()..........end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final SyncClass syncClass1 = new SyncClass();
        final SyncClass syncClass2 = new SyncClass();
        new Thread(()->{
            syncClass1.f();
        }).start();
        new Thread(syncClass2::g).start();
    }
}