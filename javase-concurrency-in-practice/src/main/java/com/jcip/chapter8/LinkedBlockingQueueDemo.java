package com.jcip.chapter8;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingQueueDemo {

    public static void main(String[] args) {
        /**
         * 如果ThreadPoolExecutor搭配的是无界队列（如LinkedBlockedingQueue)，则不会拒绝任何任务（应对队列大小没有限制）。
         * 这种情况下，ThreadPoolExecutor最多仅会按照最小线程数创建线程，
         * 也就是说，最大线程数被忽略了。
         * 如果最大线程数和最小线程数相同，则这种选择和配置了固定线程数的传统线程池运行机制最为接近
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10,
                                                             1L, TimeUnit.SECONDS,
                                                             new LinkedBlockingQueue<>());
        for (int i = 0; i < 100; i++) {
            executor.execute(new ThreadPoolTask());
        }

    }
}
