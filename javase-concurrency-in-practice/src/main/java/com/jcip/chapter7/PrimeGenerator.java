package com.jcip.chapter7;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PrimeGenerator implements Runnable{

    private static final ExecutorService exec = Executors.newCachedThreadPool();
    private final List<BigInteger> primes = new ArrayList<>();
    private volatile boolean cancelled;


    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        //任务在何时（when)检查是否已经取消了请求
        while (!cancelled){
            p = p.nextProbablePrime();
            synchronized (this){
                primes.add(p);
            }
        }
        //响应取消请求时，执行了哪些（what)操作
        exec.shutdown();
    }
    public void cancel(){
        cancelled = true;
    }
    public synchronized List<BigInteger> get(){
        return new ArrayList<>(primes);
    }

    static List<BigInteger> aSecondOfPrimes() throws InterruptedException {
        final PrimeGenerator primeGenerator = new PrimeGenerator();
        exec.execute(primeGenerator);
        try {
            TimeUnit.SECONDS.sleep(1);
        }finally {
            //how 如何请求取消该任务
            primeGenerator.cancel();
        }
        return primeGenerator.get();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(PrimeGenerator.aSecondOfPrimes());
    }
}
