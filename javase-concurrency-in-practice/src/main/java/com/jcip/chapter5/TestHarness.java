package com.jcip.chapter5;

import java.util.concurrent.CountDownLatch;

public class TestHarness {
    public long timeTasks(int nThreads,Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);
        for (int i = 0; i < nThreads; i++) {
            final Thread thread = new Thread(() -> {
                try {
                    startGate.await();//② 开始放行
                    //note-> try {} finally{} 的写法 ，一定得确保endGate.countDown()地执行
                    try {
                        task.run();//③ n个Thread 执行
                    }finally {
                        endGate.countDown();//④ 每个线程执行完成之后进行 countDown
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
        }
        final long start = System.nanoTime();
        startGate.countDown();//①一旦执行成功
        endGate.await();//⑤ 开始放行，进行时间上得统计
        return System.nanoTime() - start;
    }

    public static void main(String[] args) throws InterruptedException {
        final TestHarness testHarness = new TestHarness();
        final long time = testHarness.timeTasks(3, () -> {
            System.out.println("hello");
        });
        System.out.println(time);

    }
}
