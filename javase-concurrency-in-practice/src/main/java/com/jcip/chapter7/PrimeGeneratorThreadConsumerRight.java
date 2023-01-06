package com.jcip.chapter7;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PrimeGeneratorThreadConsumerRight {
    private static final ScheduledExecutorService cancelExec = new ScheduledThreadPoolExecutor(1);

    static void timedRun(final Runnable runnable,long timeOut, TimeUnit unit) throws InterruptedException {
        class RethrowableTask implements Runnable{
            private volatile Throwable throwable;
            @Override
            public void run() {
                try {
                    runnable.run();
                }catch (Throwable e){
                    this.throwable = e;
                }
            }
            void rethrow(){
                if(throwable!=null){
                    try{
                        if(throwable instanceof RuntimeException){
                            throw new RuntimeException(throwable);
                        }else if(throwable instanceof Error){
                            throw (Error)throwable;
                        }else {
                            throw new IllegalStateException("Not unchecked",throwable);
                        }
                    }finally {
                        cancelExec.shutdown();
                    }
                }
            }
        }
        final RethrowableTask task = new RethrowableTask();
        final Thread thread = new Thread(task);
        thread.start();
        cancelExec.schedule(thread::interrupt,timeOut,unit);
        thread.join(unit.toMillis(timeOut));
        task.rethrow();
    }

    public static void main(String[] args) {
        final PrimeGeneratorRunnable generatorRunnable = new PrimeGeneratorRunnable();
        try {
            timedRun(generatorRunnable, 1, TimeUnit.SECONDS);
        }catch (Exception e){
            System.err.println("异常得到了处理");
        }
        System.out.println(generatorRunnable.get());
    }
}
