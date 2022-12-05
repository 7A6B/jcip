package com.jcip.chapter5.performance;

import java.util.ArrayList;

public class CollectionData<T> extends ArrayList<T> {

    public CollectionData(Generator<T> gen, int quantity) {
        //todo 什么时候需要显示的调用super
        for (int i = 0; i < quantity; i++) {
            add(gen.next());
        }
    }

    // A generic convenience method:
    public static <T> CollectionData<T> list(Generator<T> gen, int quantity) {
        return new CollectionData<T>(gen, quantity);
    }
} ///:~
