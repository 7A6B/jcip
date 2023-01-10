package com.jcip.chapter7;

import java.util.concurrent.TimeUnit;

class ADaemon implements Runnable{

    @Override
    public void run() {
        try{
            System.out.println("开启 ADaemon");
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            System.out.println("通过异常退出了方法的执行");
        } finally {
            System.out.println("这会一直运行嘛？");
        }
    }
}
public class DaemonsDontRunFinally {

    public static void main(String[] args) {
        final Thread thread = new Thread(new ADaemon());
        thread.setDaemon(true);
        thread.start();
    }
}
