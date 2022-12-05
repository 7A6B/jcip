package com.jcip.chapter1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class CpuAndThread {

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
           while (true){
               System.out.println("请输入信息：");
               final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
               try {
                   final String str = reader.readLine();
                   System.out.println("==>"+str);
               }catch (IOException e){
                   throw new RuntimeException(e);
               }
           }
        });
        Thread t2 = new Thread(()->{
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("每两秒执行一次，表示你在干其他的事情");
            }
        });

        t2.start();
        t1.start();
    }
}
