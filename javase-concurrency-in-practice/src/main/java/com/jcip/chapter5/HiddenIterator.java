package com.jcip.chapter5;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HiddenIterator {
    private final Set<Integer> set = new HashSet<Integer>();
    public synchronized void add(Integer i){
        set.add(i);
    }
    public synchronized void remove(Integer i){
        set.remove(i);
    }
    public void addTenThings(){
        final Random random = new Random();
        for (int i = 0; i < 10; i++) {
            add(random.nextInt());
        }
        System.out.println("Debug: added ten elements to :"+set);
    }

    public static void main(String[] args) {
        final HiddenIterator iterator = new HiddenIterator();
        while (true){
            new Thread(iterator::addTenThings).start();
            new Thread(()->{
                iterator.add(111111);
            }).start();
            if(Thread.activeCount() ==20){
                System.out.println(Thread.activeCount()+"--------------");
                break;
            }
        }
    }
}
