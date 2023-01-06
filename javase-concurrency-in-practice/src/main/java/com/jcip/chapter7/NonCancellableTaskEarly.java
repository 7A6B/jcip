package com.jcip.chapter7;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


public class NonCancellableTaskEarly implements Runnable{
    private final BlockingQueue<Task> queue;
    NonCancellableTaskEarly(BlockingQueue<Task> queue){
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
        while (true){
            try {
                System.out.println("-------------");
                return queue.take();
            } catch (InterruptedException e) {
                System.out.println("进入异常后，线程的中断状态为=>"+Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt();
                System.out.println("Thread.currentThread().interrupt();恢复线程的中断状态为=>"
                                           +Thread.currentThread().isInterrupted());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Task> queue = new ArrayBlockingQueue<>(10);
        final NonCancellableTaskEarly cancellableTaskEarly = new NonCancellableTaskEarly(queue);
        final Thread thread = new Thread(cancellableTaskEarly);
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        thread.interrupt();
    }
}
/**
 * 1,代码运行的时候，queue如果为空的话，此时 queue.take();阻塞
 *       public E take() throws InterruptedException {
 *         final ReentrantLock lock = this.lock;
 *         lock.lockInterruptibly();
 *         try {
 *             while (count == 0)
 *                 notEmpty.await();//在次阻塞，发起打断请求的时候
 *             return dequeue();
 *         } finally {
 *             lock.unlock();
 *         }
 *     }
 *
 * 2，发起 thread.interrupt();打断请求
 *       public final void await() throws InterruptedException {
 *             if (Thread.interrupted())
 *                 throw new InterruptedException();
 *             Node node = addConditionWaiter();
 *             int savedState = fullyRelease(node);
 *             int interruptMode = 0;
 *             while (!isOnSyncQueue(node)) {
 *                 LockSupport.park(this);
 *                 if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
 *                     break;
 *             }
 *             if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
 *                 interruptMode = REINTERRUPT;
 *             if (node.nextWaiter != null) // clean up if cancelled
 *                 unlinkCancelledWaiters();
 *             if (interruptMode != 0)
 *                 reportInterruptAfterWait(interruptMode);//如果发起打断请求，会进行report
 *         }
 *
 *          private void reportInterruptAfterWait(int interruptMode)
 *             throws InterruptedException {
 *             if (interruptMode == THROW_IE)
 *                 throw new InterruptedException(); //在此会进行异常的抛出
 *             else if (interruptMode == REINTERRUPT)
 *                 selfInterrupt();
 *         }
 *
 *   3，在抛出异常后，进去代码的catch块里，执行恢复中断状态的代码（Thread.currentThread.interrupt);
 *      异常会清除中断状态，当恢复后线程的中断状态即为true(Thread.interrupted()=true);
 *   4,异常处理完成之后（中断策略执行完之后），将进行下一次循环 会接着走queue.take();
 *
 *   5, 此时会进入lock.lockInterruptibly();
 *      public E take() throws InterruptedException {
 *         final ReentrantLock lock = this.lock;
 *         lock.lockInterruptibly();
 *         try {
 *             while (count == 0)
 *                 notEmpty.await();
 *             return dequeue();
 *         } finally {
 *             lock.unlock();
 *         }
 *     }
 *
 *     public void lockInterruptibly() throws InterruptedException {
 *         sync.acquireInterruptibly(1);
 *     }
 *
 *     6，在sync.acquireInterruptibly(1);代码中会进行线程状态的判断
 *      public final void acquireInterruptibly(int arg)
 *             throws InterruptedException {
 *         if (Thread.interrupted())//进行线程的判断
 *             throw new InterruptedException();//重新抛出异常
 *         if (!tryAcquire(arg))
 *             doAcquireInterruptibly(arg);
 *     }
 */