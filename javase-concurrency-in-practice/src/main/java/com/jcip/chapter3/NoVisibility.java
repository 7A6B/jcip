package com.jcip.chapter3;

public class NoVisibility {
    private static boolean read;//初始化为默认值 false;
    private static int number;//初始化为默认值0

    private static class ReaderThread extends Thread{

        @Override
        public void run() {
            while (!read){
                Thread.yield();//线程让步，同级别的线程谁能抢到CPU的概率是一样的
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        read = true;
    }
}
