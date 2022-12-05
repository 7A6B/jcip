package com.jcip.chapter2;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SynchronizedComparison {
    static void test(Guard guard){
        final List<CompletableFuture<Void>> callers = Stream.of(new Caller(guard), new Caller(guard), new Caller(guard),
                                                                new Caller(guard)).map(CompletableFuture::runAsync)
                .collect(Collectors.toList());
        callers.forEach(CompletableFuture::join);
        System.out.println(guard);
    }

    public static void main(String[] args) {
        test(new CriticalSection());
        test(new SynchronizedMethod());
    }
}
