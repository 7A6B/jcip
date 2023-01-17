package com.jcip.chapter8;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import jdk.nashorn.internal.runtime.Timing;

class TimeTask implements Runnable {

    private final static Random random = new Random(47);
    final int id;

    public TimeTask(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TimeTask{" + "id=" + id + '}';
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(60));
            } catch (InterruptedException e) {
                //
            }
        }
    }
}

public class TimingThreadPool extends ThreadPoolExecutor {

    public TimingThreadPool() {
        super(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final Logger logger = Logger.getLogger("TimingThreadPool");
    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        logger.info(String.format("Thread %s,  start %s", t, r));
        startTime.set(System.currentTimeMillis());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try {
            long endTime = System.currentTimeMillis();
            long taskTime = endTime - startTime.get();
            numTasks.incrementAndGet();
            totalTime.addAndGet(taskTime);
            logger.info(String.format("Throwable %s,  end %s , time=%d ms", t, r, taskTime));
        } finally {
            super.afterExecute(r, t);
        }
    }

    @Override
    protected void terminated() {
        try {
            logger.info(String.format("Terminated: avg time=%d ms", totalTime.get() / numTasks.get()));
        }finally {
            super.terminated();
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor= new TimingThreadPool();
        for (int i = 0; i < 5; i++) {
            executor.execute(new TimeTask(i));
        }
        executor.shutdown();
    }
}
