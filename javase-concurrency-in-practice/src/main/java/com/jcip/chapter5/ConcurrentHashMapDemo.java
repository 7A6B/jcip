package com.jcip.chapter5;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class ConcurrentHashMapDemo {

    public static void main(String[] args) {
        final ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
        concurrentHashMap.put("key1","value1");
        concurrentHashMap.put("key2","value2");
        concurrentHashMap.put("key3","value3");
        concurrentHashMap.put("key4","value4");
        concurrentHashMap.put("key5","value5");

        new Thread(()->{
            System.out.println(concurrentHashMap.size());
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(concurrentHashMap.size());
        }).start();

        new Thread(()->{
            concurrentHashMap.put("key6","value6");
        }).start();
    }
}
