package com.jcip.chapter1;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimerTaskNotSafe {

    private int value;
    public  int getNext(){
        return value++;
    }

    public static void main(String[] args) {
        final TimerTaskNotSafe notSafe = new TimerTaskNotSafe();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName()+" ->"+notSafe.getNext());
            }
        }).start();
        new Thread(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName()+" ->"+notSafe.getNext());
                }
            }
        }).start();
    }
}