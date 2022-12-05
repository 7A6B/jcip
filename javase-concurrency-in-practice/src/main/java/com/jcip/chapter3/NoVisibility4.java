package com.jcip.chapter3;

import java.util.concurrent.TimeUnit;

public class NoVisibility4 {
    private volatile boolean flag = true;

    void doSth(){
        System.out.println("-------start-------");
        while (flag){

        }
        System.out.println("-------end---------");
    }

    public static void main(String[] args) {
        final NoVisibility4 noVisibility4 = new NoVisibility4();
        new Thread(noVisibility4::doSth).start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        noVisibility4.flag = false;
    }
}
