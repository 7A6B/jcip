package com.jcip.chapter8;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import javax.management.relation.RelationNotFoundException;

class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {

    private final Random random = new Random(47);
    private static int counter = 0;
    private final int id = counter++;
    private final int priority;
    protected static List<PrioritizedTask> sequence = new ArrayList<>();

    public PrioritizedTask(int priority) {
        this.priority = priority;
        sequence.add(this);
    }

    //谁的数字大谁优先级高
    @Override
    public int compareTo(PrioritizedTask o) {
        return Integer.compare(o.priority, priority);
    }

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(250));
        } catch (InterruptedException e) {
            //nothing to do
        }
        System.out.println(this);
    }

    @Override
    public String toString() {
        return String.format("[%1$-3d]", priority) + " Task " + id;
    }

    public static class EndSentinel extends PrioritizedTask{
        private final ExecutorService exec;
        public EndSentinel(ExecutorService e){
            super(-1);//设置最低的优先级
            exec = e;
        }

        @Override
        public void run() {
            System.out.println(this + " 调用 shutdownNow()");
            exec.shutdownNow();
        }
    }
}

class PrioritizedTaskProducer implements Runnable{
    private final Random random = new Random(47);
    private final Queue<Runnable> queue;
    private final ExecutorService exec;
    public PrioritizedTaskProducer(Queue<Runnable> queue, ExecutorService exec){
        this.queue =queue;
        this.exec = exec;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            //构建20 优先级从1-10的任务
            queue.add(new PrioritizedTask(random.nextInt(10)));
        }
        try {
            TimeUnit.MILLISECONDS.sleep(100);//0.1秒后来了一个VIP
            queue.add(new PrioritizedTask(10));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        queue.add(new PrioritizedTask.EndSentinel(exec));
        System.out.println("-----------生产者任务结束------------");
    }
}
class PrioritizedTaskConsumer implements Runnable{
    private PriorityBlockingQueue<Runnable> queue;
    public PrioritizedTaskConsumer(PriorityBlockingQueue<Runnable> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                queue.take().run();
            }
        }catch (InterruptedException e){
            //
        }
        System.out.println("=============消费者处理结束============");
    }
}
public class PriorityBlockingQueueDemo {

    public static void main(String[] args) {
        final ExecutorService exec = Executors.newCachedThreadPool();
        final PriorityBlockingQueue<Runnable> priorityBlockingQueue = new PriorityBlockingQueue<>();
        exec.execute(new PrioritizedTaskProducer(priorityBlockingQueue,exec));
        exec.execute(new PrioritizedTaskConsumer(priorityBlockingQueue));
    }
}
