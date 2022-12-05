package com.jcip.chapter3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Pool<T> {
    private int size;//标识默认端的大小
    private List<T> items = new ArrayList<>();//存放对象的池子
    private volatile boolean[] checkedOut;//检出的标识
    private Semaphore available;

    public Pool(Class<T> classObject,int size){
        this.size = size;
        checkedOut = new boolean[size];
        available = new Semaphore(size,true);
        for (int i = 0; i < size; i++) {
            try {
                items.add(classObject.newInstance());
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    public T checkOut() throws InterruptedException {
        available.acquire();
        return getItem();
    }

    private synchronized T getItem(){
        for (int i =0;i< size;++i){
            if(!checkedOut[i]){
                checkedOut[i] = true;//标识该对象已经被使用了
                return items.get(i);
            }
        }
        return null;
    }
    public void checkIn(T t){
        if(releaseItem(t)){
            available.release();
        }
    }
    private synchronized boolean releaseItem(T item){
        final int index = items.indexOf(item);
        if(index == -1) return false;
        if(checkedOut[index]){
            checkedOut[index] = false;
            return true;
        }
        return false;
    }
}
