package com.jcip.chapter2;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Guard {
    AtomicLong callCount = new AtomicLong();
    public abstract void method();

    @Override
    public String toString() {
        return getClass().getSimpleName()+" : "+callCount.get();
    }
}
