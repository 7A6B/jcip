package com.jcip.chapter8;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

public class SaturationPolicies {

    public static void main(String[] args) {
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 8, 1L,
                                                                   TimeUnit.SECONDS,
                                                                   new LinkedBlockingQueue<>(10),
                                                                   //new DiscardPolicy());
                                                                    //new DiscardOldestPolicy());
                                                                    new CallerRunsPolicy());


        for (int i = 0; i < 100; i++) {
            try {
                executor.execute(new ThreadPoolTask());
            }catch (Exception e){
                //throw new RuntimeException(e);
                System.err.println(e.getMessage());
            }
        }
        executor.shutdown();
    }
}
