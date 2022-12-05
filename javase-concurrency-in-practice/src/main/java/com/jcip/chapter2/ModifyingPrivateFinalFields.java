package com.jcip.chapter2;

import java.lang.reflect.Field;

class WithPrivateFinalFiled{
    private int i = 1;
    private final String str = "I'm final";
    private String s2 = "Am I safe?";

    @Override
    public String toString() {
        return "i = "+i+","+str +","+s2;
    }
}
public class ModifyingPrivateFinalFields {

    public static void main(String[] args) throws Exception {
        final WithPrivateFinalFiled wpf = new WithPrivateFinalFiled();
        System.out.println(wpf);
        Field field = wpf.getClass().getDeclaredField("i");
        field.setAccessible(true);
        field.setInt(wpf,47);
        System.out.println(wpf);

        field = wpf.getClass().getDeclaredField("str");
        field.setAccessible(true);
        field.set(wpf,"can I modify?");
        System.out.println(wpf);

        field = wpf.getClass().getDeclaredField("s2");
        field.setAccessible(true);
        field.set(wpf,"I'm not safe");
        System.out.println(wpf);
    }
}
