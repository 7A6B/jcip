package com.jcip.chapter7;

import java.util.concurrent.TimeUnit;

class NeedsCleanup{
    private final int id;
    public NeedsCleanup(int id){
        this.id = id;
        System.out.println("构造-->NeedsCleanup "+id+"<--");
    }
    public void cleanup(){
        System.out.println("清理==>NeedsCleanup "+id+"<==");
    }
}
class Blocked implements Runnable{

    private volatile  double d = 0.0;

    @Override
    public void run() {
        //检测当前线程中断状态
        while (!Thread.interrupted()){
            final NeedsCleanup n1 = new NeedsCleanup(1);
            try{
                final NeedsCleanup n2 = new NeedsCleanup(2);
                try{
                    for (int i = 0; i < 2500000; i++) {
                        d = d + (Math.PI+Math.E)/d;
                    }
                    System.out.println("耗时操作运行结束");
                }finally {
                    n2.cleanup();
                }
            }finally {
                n1.cleanup();
            }
        }
        System.out.println("通过while(!Thread.interrupted())退出循环");
    }
}
public class InterruptingIdiom2 {

    public static void main(String[] args) throws InterruptedException {
        final Thread t = new Thread(new Blocked());
        t.start();
        TimeUnit.MILLISECONDS.sleep(1100);
        t.interrupt();
    }
}
