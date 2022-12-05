package com.jcip.chapter5;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class VectorV1 {

    private static String getLast(Vector<String> list) {
        final int lastIndex = list.size() - 1;
        try {
            TimeUnit.NANOSECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return list.get(lastIndex);
    }

    private static void deleteLast(Vector<String> list) {
        int lastIndex = list.size() - 1;
        list.remove(lastIndex);
    }

    public static void main(String[] args) {
        final Vector<String> strings = new Vector<String>() {
            {
                add("a");
                add("b");
                add("c");
                add("d");
            }
        };
        new Thread(() -> {
            System.out.println(getLast(strings));
        }).start();
        new Thread(() -> {
            deleteLast(strings);
        }).start();

    }
}
