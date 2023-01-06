package com.jcip.chapter7;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PrimeGeneratorRunnable implements Runnable{

    private final List<BigInteger> primes = new ArrayList<>();
    private volatile boolean cancelled;


    @Override
    public void run() {
         BigInteger p = BigInteger.ONE;
        while (!cancelled){
            p = p.nextProbablePrime();
            synchronized (this){
                primes.add(p);
            }
            if(primes.size() == 10){
                throw new RuntimeException("已经产生了10个素数，任务被取消");
            }
        }
    }
    public void cancel(){
        cancelled = true;
    }
    public synchronized List<BigInteger> get(){
        return new ArrayList<>(primes);
    }
}
