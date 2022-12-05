package com.jcip.chapter3;

import java.util.concurrent.TimeUnit;

public class NoVisibility2 {
    private static boolean read;//初始化为默认值 false;
    private static int number;//初始化为默认值0

    private static class ReaderThread extends Thread{

        @Override
        public void run() {
            while (!read){

            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        number = 42;
        read = true;
    }
}
