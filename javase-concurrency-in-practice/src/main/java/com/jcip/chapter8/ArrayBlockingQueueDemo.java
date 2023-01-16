package com.jcip.chapter8;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingQueueDemo {

    public static void main(String[] args) {
        /**
         * 1,随着任务到达并放入队列中，线程池最多会运行4个线程（corePoolSize), 此时队列被完全填满（10个处于等待的任务）
         * 2,队列已满，又有新任务过来了，此时才会启动一个新线程。这里其实并不会因为队列已满了而拒绝该任务；因为此时最大线程数将发挥作用
         * 3，当有7个任务正在处理，队列中已有10个任务，此时又来了一个新任务，线程池才会达到最大的线程数8个
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 8,
                               1L, TimeUnit.SECONDS,
                               new ArrayBlockingQueue<>(10));
        for (int i = 0; i < 100; i++) {
            //模拟0.1提交提交一个任务，每个任务执行1秒
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                executor.execute(new ThreadPoolTask());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
