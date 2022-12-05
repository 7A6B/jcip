package com.jcip.chapter3;

import java.util.concurrent.TimeUnit;

public class NoVisibility3 {
    private static boolean read;//初始化为默认值 false;
    private static int number;//初始化为默认值0

    private static class ReaderThread extends Thread{

        @Override
        public void run() {
            while (!read){
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        read = true;
        TimeUnit.NANOSECONDS.sleep(2);
        number = 42;
    }
}
