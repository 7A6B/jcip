package com.jcip.chapter2;

import java.util.concurrent.TimeUnit;

public class ReentryApp {
    public synchronized void doSth(){
        System.out.println("...doSth() start......");
        doOtherSth();
        System.out.println("...doSth()   end......");
    }

    public synchronized void doOtherSth(){
        System.out.println("...doOtherSth() start......");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("...doOtherSth()   end......");
    }

    public static void main(String[] args) {
        final ReentryApp reentryApp = new ReentryApp();
        new Thread(reentryApp::doSth).start();
    }
}
