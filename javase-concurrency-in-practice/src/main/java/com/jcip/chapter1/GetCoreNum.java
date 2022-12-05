package com.jcip.chapter1;

public class GetCoreNum {

    /**
     * note
     *  插槽:存放CPU芯片
     *  内核：CPU芯片上的处理器
     *  逻辑处理器：利用超线程技术，共享计算单元，将处理器衍生为两个逻辑的CPU用
     */
    public static void main(String[] args) {
        System.out.println("逻辑处理器==>"+Runtime.getRuntime().availableProcessors());
    }
}
