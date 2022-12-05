package com.jcip.chapter3;

import java.util.concurrent.TimeUnit;
import org.omg.CORBA.TIMEOUT;

public class ThisEscapeApp {

    public static void main(String[] args) {
        final SomeEventSource someEventSource = new SomeEventSource();

        new Thread(()->{
            final ThisEscape thisEscape = new ThisEscape(someEventSource);
        }).start();

        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        new Thread(()->{
            someEventSource.processEvent(new Event() {});
        }).start();
    }
}
