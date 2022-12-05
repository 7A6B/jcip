package com.jcip.chapter1;

import java.util.concurrent.TimeUnit;

public class UnsafeSequence {
    private int value;
    public synchronized int getNext(){
        return value++;
    }

    public static void main(String[] args) {
        final UnsafeSequence sequence = new UnsafeSequence();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName()+"-->"+sequence.getNext());
            }
        }).start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName()+"-->"+sequence.getNext());
            }
        }).start();
    }
}
