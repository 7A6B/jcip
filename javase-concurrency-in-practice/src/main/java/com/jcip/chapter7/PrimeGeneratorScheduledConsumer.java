package com.jcip.chapter7;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PrimeGeneratorScheduledConsumer {
    private static final ScheduledExecutorService cancelExec = new ScheduledThreadPoolExecutor(1);
    static void timedRun(Runnable runnable,long timeOut, TimeUnit unit){
        final Thread taskThread = Thread.currentThread();
        cancelExec.schedule(taskThread::interrupt,timeOut,unit);
        runnable.run();
    }

    public static void main(String[] args) {
        final PrimeGeneratorScheduledProducer primeTask = new PrimeGeneratorScheduledProducer();
        try {
            timedRun(primeTask, 1, TimeUnit.SECONDS);
        }catch (RuntimeException e){
            System.err.println("获取了异常");
            cancelExec.shutdown();
        }
        final List<BigInteger> primes = primeTask.get();
        System.out.println(primes);
    }
}
