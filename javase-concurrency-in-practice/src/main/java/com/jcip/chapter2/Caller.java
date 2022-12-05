package com.jcip.chapter2;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class Caller implements Runnable{

    private Guard guard;

    Caller(Guard guard){
        this.guard = guard;
    }
    private AtomicLong successfulCalls = new AtomicLong();
    private AtomicBoolean stop = new AtomicBoolean(false);

    @Override
    public void run() {
        //执行2.5秒时间
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                stop.set(true);
            }
        },2500);
        while (!stop.get()){
            guard.method();
            successfulCalls.incrementAndGet();
        }
        System.out.println("一共执行了 ->"+successfulCalls.get());
    }
}
