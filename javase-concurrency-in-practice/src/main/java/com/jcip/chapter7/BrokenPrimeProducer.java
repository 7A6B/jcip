package com.jcip.chapter7;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BrokenPrimeProducer extends Thread{
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled = false;
    BrokenPrimeProducer(BlockingQueue<BigInteger> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            //③ 此时生产者是无法检查该标志的，因为代码在queue.put()阻塞着
            while (!cancelled) {
                //① 生产者的速率 > 消费者的速率
                TimeUnit.MILLISECONDS.sleep(1);
                //此时 put()将在阻塞队列已满的情况下 会阻塞
                queue.put(p = p.nextProbablePrime());
                System.out.println("生产者放入素数后的queue的大小==>"+queue.size());
            }
        }catch (InterruptedException e){}
    }
    public void  cancel(){
        cancelled = true;
    }
}
