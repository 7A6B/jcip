package com.jcip.chapter6;

import com.jcip.chapter4.DelegatingVehicleTracker;
import com.jcip.chapter6.DelayedTask.EndSentinel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class DelayedTask implements Runnable, Delayed{
    private static int counter = 0;
    private final int id = counter++;
    private final int delta;
    private final long trigger;
    protected static List<DelayedTask> sequence = new ArrayList<>();
    public DelayedTask(int delayInMilliseconds){
        delta = delayInMilliseconds;
        trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delta,TimeUnit.MILLISECONDS);
        sequence.add(this);
    }

    @Override
    public int compareTo(Delayed o) {
        DelayedTask that = (DelayedTask) o;
        if (trigger < that.trigger){
            return -1;
        }
        if(trigger > that.trigger){
            return 1;
        }
        return 0;
    }

    @Override
    public void run() {
        System.out.println(this + " ");
    }

    @Override
    public String toString() {
        return String.format("[%1$-4d]",delta)+" Task "+id;
    }

    public String summary(){
        return "(" + id + ": "+ delta+ ")";
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(),TimeUnit.NANOSECONDS);
    }

    //末端哨兵机制
    public static class EndSentinel extends DelayedTask{
        private ExecutorService exec;
        public EndSentinel(int delay,ExecutorService exec){
            super(delay);
            this.exec= exec;
        }

        @Override
        public void run() {
            for (DelayedTask task : sequence) {
                System.out.print(task.summary() + " ");
            }
            System.out.println();
            System.out.println(this + "调用shutDownNow()");
            exec.shutdownNow();
        }
    }
}



class DelayedTaskConsumer implements Runnable{
    private DelayQueue<DelayedTask> queue;
    public DelayedTaskConsumer(DelayQueue<DelayedTask> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                queue.take().run();
            }
        }catch (InterruptedException e){}
        System.out.println("DelayedTaskConsumer 消费结束");
    }
}

public class DelayedQueueDemo {

    public static void main(String[] args) {
        final Random random = new Random(47);
        final ExecutorService exec = Executors.newCachedThreadPool();
        final DelayQueue<DelayedTask> queue = new DelayQueue<>();
        for (int i = 0; i < 20; i++) {
            queue.put(new DelayedTask(random.nextInt(5000)));
        }
        queue.add(new EndSentinel(5000,exec));

        exec.execute(new DelayedTaskConsumer(queue));

    }
}
