package com.jcip.chapter6;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class CompletionTask implements Callable<String>{
    private static int counter = 0;
    private final int id = counter++;
    private final int delay;
    public CompletionTask(int delay){
        this.delay = delay;
    }

    @Override
    public String call() throws Exception {
        TimeUnit.MILLISECONDS.sleep(delay);
        return "["+delay+"]CompletionTask=>"+id;
    }
}
public class CompletionServiceDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final Random random = new Random(47);
        final ExecutorService exec = Executors.newCachedThreadPool();
        CompletionService<String> completionService = new ExecutorCompletionService<>(exec);
        for (int i = 0; i < 20; i++) {
            final CompletionTask task = new CompletionTask(random.nextInt(5000));
            completionService.submit(task);
        }
        System.out.println("在main中 do sth");
        for (int i = 0; i < 20; i++) {
            final Future<String> future = completionService.take();
            System.out.println(future.get());
        }
        exec.shutdown();
    }
}
