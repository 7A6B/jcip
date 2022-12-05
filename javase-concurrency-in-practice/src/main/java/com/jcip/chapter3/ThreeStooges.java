package com.jcip.chapter3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public final class ThreeStooges {
    private final Set<String> stooges = new HashSet<>();
    public ThreeStooges(){
        stooges.add("a");
        stooges.add("b");
        stooges.add("c");
    }

    public boolean isStooges(String name){
        return stooges.contains(name);
    }
    public String getStoogeNames(){
        List<String> stooges = new Vector<>();
        stooges.add("a");
        stooges.add("b");
        stooges.add("c");
        return stooges.toString();
    }
}
