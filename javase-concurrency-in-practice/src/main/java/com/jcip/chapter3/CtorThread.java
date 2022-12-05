package com.jcip.chapter3;

import java.util.concurrent.TimeUnit;

public class CtorThread {
    private String str;
    CtorThread(String str){
        new Thread(this::readStr).start();
        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.str = str;
    }

    void readStr(){

        System.out.println("readStr=>"+str);
    }

    public static void main(String[] args) {
        new CtorThread("hello");
    }
}
