package com.jcip.chapter5;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierDemo {

    private static final Random random = new Random(47);
    public static void main(String[] args) {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3,()->{
            while (!Thread.interrupted()){
                try {
                    TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("------------");
            }
        });
        final ExecutorService threadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            threadPool.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(1);
                    cyclicBarrier.await();
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
    }
}
