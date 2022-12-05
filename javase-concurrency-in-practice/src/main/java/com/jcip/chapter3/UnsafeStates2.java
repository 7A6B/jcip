package com.jcip.chapter3;

import java.util.Arrays;

public class UnsafeStates2 {
    private String[] states = new String[]{"AK","AL"};
    public String[] getStates(){
        return states == null ?null : Arrays.copyOf(states,states.length);
    }

    public static void main(String[] args) {
        final UnsafeStates2 unsafeStates2 = new UnsafeStates2();
        unsafeStates2.getStates()[1] = "ZK";
        System.out.println(Arrays.toString(unsafeStates2.getStates()));
    }
}
