package com.jcip.chapter5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SameTimeExecApp {

    public static void main(String[] args) {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(()->{
                try {
                    final int indexNum = cyclicBarrier.await();
                    System.out.println(indexNum);
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
                //note-> 微秒级别保持一致 纳秒不一致
                System.out.println("exec at the same time ==> "+System.currentTimeMillis());
            });
        }
        executorService.shutdown();
    }
}
