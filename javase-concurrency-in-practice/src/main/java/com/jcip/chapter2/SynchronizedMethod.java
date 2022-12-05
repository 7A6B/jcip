package com.jcip.chapter2;

import java.util.concurrent.TimeUnit;

public class SynchronizedMethod extends Guard{

    @Override
    public synchronized void method() {
        try {
            TimeUnit.MILLISECONDS.sleep(10);
            callCount.incrementAndGet();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
