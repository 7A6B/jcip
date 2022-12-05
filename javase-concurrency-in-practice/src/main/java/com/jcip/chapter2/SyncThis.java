package com.jcip.chapter2;

import java.util.concurrent.TimeUnit;

public class SyncThis {
    public void f(){
        synchronized (this){
            try {
                System.out.println("f()..........begin");
                TimeUnit.SECONDS.sleep(3);
                System.out.println("f()...........end");
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public synchronized void g(){
        try {
            System.out.println("g()..........begin");
            TimeUnit.SECONDS.sleep(5);
            System.out.println("g()..........end");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final SyncThis syncThis = new SyncThis();
        new Thread(syncThis::f).start();
        new Thread(syncThis::g).start();
    }
}
