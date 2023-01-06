package com.jcip.chapter7;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

public class PrimeGeneratorScheduledProducer implements Runnable{

    private final List<BigInteger> primes = new ArrayList<>();

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!Thread.interrupted()){
            p = p.nextProbablePrime();
            synchronized (this){
                primes.add(p);
            }
            if(primes.size() ==10){
                throw new RuntimeException("已经产生了10个素数，任务取消");
            }
        }
        System.out.println("线程被中断，任务取消");
    }

    public List<BigInteger> get() {
        return new ArrayList<>(primes);
    }
}
