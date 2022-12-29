package com.jcip.chapter7;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BrokenPrimeConsumer {
    private final BlockingQueue<BigInteger> queue;
    BrokenPrimeConsumer(BlockingQueue<BigInteger> queue){
        this.queue = queue;
    }
    void consumePrimes() throws InterruptedException {
        final BrokenPrimeProducer producer = new BrokenPrimeProducer(queue);
        producer.start();
        try{
            //② 消费者当看到阻塞队列已满10个，希望取消任务，消费者将调用cancel()
            while (needMorePrimes()){
                consume(queue.take());
            }
        }finally {
            producer.cancel();
        }
    }

    private void consume(BigInteger num) {
        try {
            TimeUnit.MILLISECONDS.sleep(3);
            System.out.println("每3毫秒消费一个："+num);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    private boolean needMorePrimes() {
        if(queue.size()==10){
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        final BlockingQueue<BigInteger> blockingQueue = new ArrayBlockingQueue<>(10);
        final BrokenPrimeConsumer consumer = new BrokenPrimeConsumer(blockingQueue);
        consumer.consumePrimes();
    }
}
