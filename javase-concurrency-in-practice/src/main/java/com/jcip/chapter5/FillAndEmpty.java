package com.jcip.chapter5;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

class DataBuffer {

    private final ConcurrentSkipListSet<String> set = new ConcurrentSkipListSet<>();

    public ConcurrentSkipListSet<String> getSet() {
        return set;
    }

    public boolean isFull() {
        return set.size() == 10;//模拟一共只有10个数据容量
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }
}

public class FillAndEmpty {

    Exchanger<DataBuffer> exchanger = new Exchanger<DataBuffer>();
    DataBuffer initialEmptyBuffer = new DataBuffer();
    DataBuffer initialFullBuffer = new DataBuffer();

    class FillingLoop implements Runnable {

        int count = 0;

        public void run() {
            DataBuffer currentBuffer = initialEmptyBuffer;//初始化的空buffer
            try {
                while (currentBuffer != null) {
                    addToBuffer(currentBuffer);
                    if (currentBuffer.isFull()) {
                        System.out.println("-------isFull---------" + currentBuffer.getSet());
                        currentBuffer = exchanger.exchange(currentBuffer);//将装满的currentBuffer进行交换
                        System.out.println("从消费端交换过来的元素==>" + currentBuffer.getSet() + "  是否为空呢");
                        TimeUnit.SECONDS.sleep(5);
                    }
                }
            } catch (InterruptedException ex) {
            }
        }

        private void addToBuffer(DataBuffer currentBuffer) {
            currentBuffer.getSet().add("aaa==>" + count++);
        }
    }


    class EmptyingLoop implements Runnable {
        int count = 0;

        public void run() {
            DataBuffer currentBuffer = initialFullBuffer;
            try {
                while (currentBuffer != null) {
                    takeFromBuffer(currentBuffer);
                    if (currentBuffer.isEmpty()) {
                        currentBuffer = exchanger.exchange(currentBuffer);
                        System.out.println("从生产端交换过来的元素==>"+currentBuffer.getSet()+" 是否是满的呢");
                    }
                }
            } catch (InterruptedException ex) { }
        }

        private void takeFromBuffer(DataBuffer currentBuffer) {
            if(!currentBuffer.isEmpty()){
                final ConcurrentSkipListSet<String> set = currentBuffer.getSet();
                String str = "aaa==>"+count++;
                set.remove(str);//模拟使用
                System.out.println("使用元素-->"+str);
            }
        }
    }

    void start() {
        new Thread(new FillingLoop()).start();
        new Thread(new EmptyingLoop()).start();
    }

    public static void main(String[] args) {
        final FillAndEmpty fillAndEmpty = new FillAndEmpty();
        fillAndEmpty.start();
    }
}