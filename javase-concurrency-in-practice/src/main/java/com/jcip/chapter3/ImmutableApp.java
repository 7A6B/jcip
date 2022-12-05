package com.jcip.chapter3;

import java.util.concurrent.TimeUnit;

class Joo{
    String str = "joo";
}
class Ioo{
    final Joo joo;
    Ioo(Joo joo){
        this.joo = joo;
    }
    public Joo getJoo(){return joo;}
}

public class ImmutableApp {

    public static void main(String[] args) {
        final Joo joo = new Joo();
        final Ioo ioo = new Ioo(joo);
        new Thread(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            System.out.println(ioo.getJoo().str);
        }).start();
        new Thread(()->{
            joo.str = "updateJoo";
        }).start();
    }
}
