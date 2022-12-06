package com.jcip.chapter5;

import java.util.concurrent.TimeUnit;

//实体类 表示一个在中断的时候需要被清理的资源
class NeedsCleanup{
    private final int id ;
    public NeedsCleanup(int ident){
        id = ident;
        System.out.println("NeedsCleanup=>"+id);
    }
    public void cleanup(){
        System.out.println("CleaningUp=>"+id);
    }
}
class Blocked implements Runnable{

    @Override
    public void run() {
        try {
            //判断线程是否被中断
            while (!Thread.interrupted()) {
                final NeedsCleanup needsCleanup = new NeedsCleanup(1);
                try {
                    System.out.println("模拟业务执行1秒钟");
                    TimeUnit.SECONDS.sleep(1);
                } finally {
                    needsCleanup.cleanup();//查看异常抛出后 资源是否能够得到清理
                }
            }
        }catch (InterruptedException e){
            System.err.println("处理中断异常");
        }
    }
}
public class InterruptingIdiom {

    public static void main(String[] args) throws InterruptedException {
        final Thread thread = new Thread(new Blocked());
        thread.start();
        TimeUnit.MILLISECONDS.sleep(1100);
        thread.interrupt();
    }
}
