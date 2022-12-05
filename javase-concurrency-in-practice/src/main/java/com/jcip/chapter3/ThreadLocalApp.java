package com.jcip.chapter3;

import java.util.concurrent.TimeUnit;

class Foo{
    String str ="foo";
}
public class ThreadLocalApp {

    public static void main(String[] args) {
        final Foo foo = new Foo();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(foo.str);
        }).start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            foo.str= "updateFoo";
        }).start();
    }
}
