package com.jcip.chapter2;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

class Foo{
    private static final ConcurrentHashMap<String,Object> hashMap = new ConcurrentHashMap<>();

    public static void putIfAbsent(String key, Function<String, Object> callback){
        if(!hashMap.contains(key)){
            hashMap.put(key,callback.apply(key));
        }
    }
    public static ConcurrentHashMap<String,Object> getHashMap(){
        return hashMap;
    }
}

public class ConcurrentHashMapTest {

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            Foo.putIfAbsent("key",str->{
                String res = str + "111111";
                System.out.println(res);
                return res;
            });
        }).start();

        new Thread(()->{
            Foo.putIfAbsent("key",str->{
                String res = str + "222222";
                System.out.println(res);
                return res;
            });
        }).start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(Foo.getHashMap());
    }
}