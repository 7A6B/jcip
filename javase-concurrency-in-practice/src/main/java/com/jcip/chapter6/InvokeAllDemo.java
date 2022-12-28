package com.jcip.chapter6;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class CountingTask implements Callable<Void>{
    private final static Random random = new Random(47);
    final int id;
    public CountingTask(int id){
        this.id = id;
    }

    @Override
    public Void call() throws Exception {
        int val = 0;
        for (int i = 0; i < 100; i++) {
            val++;
            TimeUnit.MILLISECONDS.sleep(random.nextInt(60));
        }
        System.out.println(id +" "+Thread.currentThread().getName()+" "+val);
        return null;
    }
}

public class InvokeAllDemo {

    public static void main(String[] args) throws InterruptedException {
        final ExecutorService exec = Executors.newCachedThreadPool();
        final List<CountingTask> tasks = IntStream.range(0, 10).mapToObj(CountingTask::new)
                .collect(Collectors.toList());
        exec.invokeAll(tasks,3,TimeUnit.SECONDS);
        exec.shutdown();
    }
}
