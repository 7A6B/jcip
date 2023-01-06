package com.jcip.chapter7;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class NonCancellableTaskRight implements Runnable{

    private final BlockingQueue<Task> queue;
    NonCancellableTaskRight(BlockingQueue<Task> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        Task task = null;
        while (!Thread.interrupted()){
            task = getNextTask(queue);
            if(task!=null){
                System.out.println(task);
            }else{
                System.out.println("---------任务获取结束------------");
            }
        }
    }

    private Task getNextTask(BlockingQueue<Task> queue) {
        boolean interrupted = false;
        try{
            while (true){
                try{
                    System.out.println("-----------------------");
                    return queue.take();
                }catch (InterruptedException e){
                    interrupted = true;
                    System.out.println("进入异常后，线程的中断状态为==>"+Thread.currentThread().isInterrupted());
                    return null;
                }
            }
        }finally {
            if(interrupted){
                Thread.currentThread().interrupt();
                System.out.println("finally恢复中断状态后==>"+Thread.currentThread().isInterrupted());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final BlockingQueue<Task> queue = new ArrayBlockingQueue<>(10);
        final NonCancellableTaskRight taskRight = new NonCancellableTaskRight(queue);

        try{
            for (int i = 0; i < 10; i++) {
                TimeUnit.MILLISECONDS.sleep(100);
                queue.put(new Task());
            }
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        final Thread thread = new Thread(taskRight);
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        thread.interrupt();
    }
}
