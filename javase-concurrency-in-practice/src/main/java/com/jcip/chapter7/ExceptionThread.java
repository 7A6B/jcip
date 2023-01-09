package com.jcip.chapter7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExceptionThread implements Runnable{

    @Override
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        final ExecutorService exec = Executors.newCachedThreadPool();
        try {
            exec.execute(new ExceptionThread());
        }catch (RuntimeException e){
            System.err.println("-----即使捕获也没用-------");
        }
    }
}
