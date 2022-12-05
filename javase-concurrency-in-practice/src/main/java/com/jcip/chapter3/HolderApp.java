package com.jcip.chapter3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class SomeClass{
     Holder holder;
    public void initialize(){
        holder = new Holder(42);
    }
}
public class HolderApp {

    public static void main(String[] args) throws InterruptedException {
        final SomeClass someClass = new SomeClass();
        final ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(someClass::initialize);
            try {
                someClass.holder.assertSanity();
            }catch (NullPointerException e){
                System.err.println(e.getMessage());
            }
        }
        TimeUnit.SECONDS.sleep(1);
        executorService.shutdown();
    }
}
