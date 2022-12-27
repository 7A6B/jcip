package com.jcip.chapter6;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimerDemo {

    public static void main(String[] args) throws InterruptedException {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("--------10ms---------");
            }
        },0,10);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("----------40ms---------begin---------");
                try {
                    TimeUnit.MILLISECONDS.sleep(40);
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
                System.out.println("----------40ms----------end----------");
            }
        },0);
        TimeUnit.MILLISECONDS.sleep(50);
        timer.cancel();
    }
}
