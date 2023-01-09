package com.jcip.chapter7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class RunnableTask implements Runnable{
    private final static Random random = new Random(47);
    final int id;
    public RunnableTask(int id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "RunnableTask{" + "id=" + id + '}';
    }

    @Override
    public void run() {
        System.out.println(id+" 的任务运行开始------------"+Thread.currentThread().getName());
        int val = 0;
        for (int i = 0; i < 100; i++) {
            try{
                TimeUnit.MILLISECONDS.sleep(random.nextInt(60));
                //放在休眠之后，确保有部分任务未完成；
                //放在休眠之前 将会复现书中的"误报"问题
                val++;
            }catch (InterruptedException e){
                //任务在返回时必须得维持线程得中断状态
                //因为异常处理会清除线程得中断状态，所以得进行中断状态恢复
                Thread.currentThread().interrupt();
            }
        }
        if(val ==100){
            System.out.println(id+" 的任务运行结束============"+Thread.currentThread().getName()+" "+val);
        }
    }
}

public class TrackingExecutor extends AbstractExecutorService {
    private final ExecutorService exec;
    private final Set<Runnable> tasksCancelledAtShutdown = Collections.synchronizedSet(new HashSet<>());
    public TrackingExecutor(ExecutorService exec){
        this.exec = exec;
    }

    @Override
    public void shutdown() {
        exec.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return exec.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return exec.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return exec.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return exec.awaitTermination(timeout,unit);
    }

    public List<Runnable> getCancelledTasks(){
        if(!exec.isTerminated()){
            throw new IllegalStateException();
        }
        return new ArrayList<>(tasksCancelledAtShutdown);
    }

    @Override
    public void execute(Runnable command) {
        exec.execute(()->{
            try{
                command.run();
            }finally {
                 System.out.println(Thread.currentThread().getName()+"的  "+"isShutdown()=>"+isShutdown()
                                           +"  Thread.currentThread().isInterrupted()->"
                                           +Thread.currentThread().isInterrupted()+" Runnable="+command);
                if(isShutdown()&& Thread.currentThread().isInterrupted()){
                    tasksCancelledAtShutdown.add(command);
                }
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        final TrackingExecutor exec = new TrackingExecutor(Executors.newCachedThreadPool());
        for (int i = 0; i < 10; i++) {
            final RunnableTask task = new RunnableTask(i);
            exec.execute(task);
        }
        TimeUnit.SECONDS.sleep(3);
        exec.shutdownNow();

        TimeUnit.SECONDS.sleep(1);
        final List<Runnable> cancelledTasks = exec.getCancelledTasks();
        System.out.println("已开始未结束的任务："+cancelledTasks);
    }
}
