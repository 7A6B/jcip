package com.jcip.chapter2;

import java.util.Vector;

public class VectorApp {
    private static final Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) {
        while (true){
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }
            new Thread(()->{
                for (int i = 0; i < vector.size(); i++) {
                    vector.remove(i);
                }
            }).start();

            new Thread(()->{
                for (int i = 0; i < vector.size(); i++) {
                    System.out.print(vector.get(i)+"\t");
                }
            }).start();
            while (Thread.activeCount() > 20);
        }
    }
}