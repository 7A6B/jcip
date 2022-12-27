package com.jcip.chapter6;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class OutOfTime {

    public static void main(String[] args)throws Exception {
        final Timer timer = new Timer();
        timer.schedule(new ThrowTask(),1);
        TimeUnit.SECONDS.sleep(1);
        timer.schedule(new ThrowTask(),1);
        TimeUnit.SECONDS.sleep(5);
    }
    static class ThrowTask extends TimerTask{

        @Override
        public void run() {
            throw new RuntimeException();
        }
    }
}
