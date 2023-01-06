package com.jcip.chapter7;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PrimeGeneratorRunnableConsumer {
    static List<BigInteger> aSecondOfPrimes() throws InterruptedException {
        final PrimeGeneratorRunnable generator = new PrimeGeneratorRunnable();
        try {
            new Thread(generator).start();
        }catch (RuntimeException e){
            System.err.println("即使你显示的捕获异常，也无法处理");
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        }finally {
            generator.cancel();
        }
        return generator.get();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(aSecondOfPrimes());
    }
}
