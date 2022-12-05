package com.jcip.chapter3;

import java.util.concurrent.TimeUnit;

public class ThisEscape {
    //用num标识初始化是否完成
    private final int num;
    public ThisEscape(EventSource source){
        //warn-> 1,ThisEscape 没有构造完成之后，已经将注册成功
        source.registerListener(new EventListener() {
            @Override
            public void onEvent(Event e) {
                //warn-> 2,在事件监听中去调用了ThisEscape的实例方法  -> this
                doSth(e);
            }
        });
        System.out.println("---------初始化话比较耗资源-----------");
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        num = 33;
    }

    private void doSth(Event e) {
        if(num!=44){
            System.out.println("如果构造完成的话，将不会输出这句话");
        }
    }

}
