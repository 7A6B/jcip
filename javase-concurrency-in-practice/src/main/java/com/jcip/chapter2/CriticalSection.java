package com.jcip.chapter2;

import java.util.concurrent.TimeUnit;

public class CriticalSection extends Guard{

    @Override
    public void method() {
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        synchronized (this){
            callCount.incrementAndGet();
        }
    }
}
