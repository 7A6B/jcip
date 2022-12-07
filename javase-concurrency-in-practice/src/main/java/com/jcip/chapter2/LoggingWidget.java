package com.jcip.chapter2;

/**
 * note-> 被synchronized修饰的同步块对于同一条线程来说时可重入的。这意味着同一线程反复进入同步块也不会把自己锁死
 *  被synchronized修饰的同步块在持有锁的线程执行完毕并释放锁之前，会无条件地阻塞后面其他线程地进入
 */
public class LoggingWidget extends Widget{

    @Override
    public synchronized void doSth() {
        System.out.println(Thread.currentThread().getName()+"==>"+"进入 LoggingWidget 的doSth");
        super.doSth();
        System.out.println(Thread.currentThread().getName()+"==>"+"结束 LoggingWidget 的doSth");
    }

    public static void main(String[] args) {
        Widget widget = new LoggingWidget();
        new Thread(widget::doSth).start();
    }
}