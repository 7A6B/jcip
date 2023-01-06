package com.jcip.chapter7;

public class Task{
    private static int counter = 0;
    private final int id = counter++;

    @Override
    public String toString() {
        return "Task{" + "id=" + id + '}';
    }
}