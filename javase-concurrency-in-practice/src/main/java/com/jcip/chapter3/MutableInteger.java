package com.jcip.chapter3;

public class MutableInteger {
    private int value;
    public int get(){
        return value;
    }
    public void set(int value){this.value = value;}

    public static void main(String[] args) {
        final MutableInteger mutableInteger = new MutableInteger();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(()->{
                mutableInteger.set(finalI);
            }).start();
            new Thread(()->{
                System.out.println("获取得值为=>"+mutableInteger.get());
            }).start();
        }
    }
}

