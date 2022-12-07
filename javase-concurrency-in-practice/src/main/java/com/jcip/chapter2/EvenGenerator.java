package com.jcip.chapter2;

public class EvenGenerator extends IntGenerator{

    private int currentEventValue = 0;

    @Override
    public int next() {
        ++currentEventValue;
        ++currentEventValue;
        return currentEventValue;
    }

    public static void main(String[] args) {
        final EvenGenerator generator = new EvenGenerator();
        EvenChecker.test(generator);
    }
}