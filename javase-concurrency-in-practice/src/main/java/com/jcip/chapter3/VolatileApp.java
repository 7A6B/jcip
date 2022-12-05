package com.jcip.chapter3;

import java.util.concurrent.TimeUnit;

public class VolatileApp {
    volatile boolean asleep;
    public void count(){
        int num = 0;
        while (!asleep){
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(++num +" 只羊");
        }
    }

    public static void main(String[] args) {
        final VolatileApp volatileApp = new VolatileApp();
        new Thread(volatileApp::count).start();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        volatileApp.asleep = true;
    }
}
