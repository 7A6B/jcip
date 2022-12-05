package com.jcip.chapter2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvenChecker implements Runnable{

    private final  IntGenerator generator;
    private final  int id;

    EvenChecker(IntGenerator generator,int identity){
        id = identity;
        this.generator = generator;
    }

    @Override
    public void run() {
        while (!generator.isCanceled()){
            int value = generator.next();
            if(value % 2 !=0){
                System.out.println(value+" not even "+" ,由 id为： "+ id+" 的任务发现");
                generator.cancel();
            }
        }
    }
    public static void test(IntGenerator gen,int count){
        final ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0 ;i < count; i++){
            executorService.execute(new EvenChecker(gen,i));
        }
        executorService.shutdown();
    }
    public static void test(IntGenerator gen){
        test(gen,10);
    }
}
