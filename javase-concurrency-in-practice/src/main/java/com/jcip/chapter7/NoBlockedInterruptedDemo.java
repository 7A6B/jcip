package com.jcip.chapter7;

import java.util.concurrent.TimeUnit;

class NoBlockedTask implements Runnable {

    private double d = 0.0;

    /**
     * interrupt()调用之后，此时线程的状态interrupt= true;Thread.interrupted()调用之后，清除interrupt，此时interrupt= false(
     * 即Thread.currentThread().isInterrupted())；该Thread.interrupted()调用的返回值为之前的true
     * Thread.interrupted()
     */
    @Override
    public void run() {
        long start = System.currentTimeMillis();
        try {
            //while (!Thread.interrupted()) {
            while (!Thread.currentThread().isInterrupted()){//只是返回当前线程的状态，不会清除中断状态
                for (int i = 0; i < 10000000; i++) {
                    d = d + (Math.PI + Math.E) / d;
                }
            }
            System.out.println(
                    "exist while loop-->" + (System.currentTimeMillis() - start) + ":" + Thread.currentThread()
                            .isInterrupted());
        } finally {
            System.out.println(
                    "finally-->" + (System.currentTimeMillis() - start) + ":" + Thread.currentThread()
                            .isInterrupted());
        }
    }
}

public class NoBlockedInterruptedDemo {

    public static void main(String[] args) throws InterruptedException {
        final Thread thread = new Thread(new NoBlockedTask());
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        thread.interrupt();
    }
}
