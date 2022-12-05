package com.jcip.chapter1;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.xml.crypto.Data;

public class TimerApp {

    public static void main(String[] args) {
        final Timer timer = new Timer();
       /* timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("两秒后执行");
            }
        },2000);*/

        //warn 过去的时间 会将缺失的执行补齐
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("-------------");
            }
        }, new Date(new Date().getTime() - 6000), 2000);
    }
}
