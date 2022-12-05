package com.jcip.chapter2;

//参考 Java编程思想 21.3共享受限资源
public class AtomicityTest implements Runnable{

    private int i;

    //warn-> 虽然return i为原子操作，但是缺少了同步使得其数值可以在处于不稳定的中间状态时被读取
    public int getI() {
        return i;
    }

    public synchronized void increment(){
        i++;
        i++;
    }

    @Override
    public void run() {
        while (true){
            increment();
        }
    }

    public static void main(String[] args) {
        final AtomicityTest test = new AtomicityTest();
        new Thread(test).start();
        while (true){
            final int i = test.getI();
            if(i % 2 !=0){
                System.out.println(i);
                System.exit(0);
            }
        }
    }
}
