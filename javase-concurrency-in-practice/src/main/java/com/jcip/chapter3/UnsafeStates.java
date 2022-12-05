package com.jcip.chapter3;

import java.util.Arrays;

public class UnsafeStates {
    private String[] states = new String[]{"AK","AL"};

    public String[] getStates() {
        return states;
    }

    public static void main(String[] args) {
        final UnsafeStates unsafeStates = new UnsafeStates();
        unsafeStates.states[1]="ZK";
        System.out.println(Arrays.toString(unsafeStates.getStates()));
    }
}
