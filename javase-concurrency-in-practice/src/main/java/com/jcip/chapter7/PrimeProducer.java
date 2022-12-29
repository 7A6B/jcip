package com.jcip.chapter7;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class PrimeProducer extends Thread {

    private final BlockingQueue<BigInteger> queue;
    PrimeProducer(BlockingQueue<BigInteger> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger p = BigInteger.ONE;

            while (!Thread.currentThread().isInterrupted()) {
                //TimeUnit.MILLISECONDS.sleep(1);
                queue.put(p = p.nextProbablePrime());
                System.out.println("生成者放入后,queue的大小=>"+queue.size());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void cancel(){
        interrupt();
    }
}
