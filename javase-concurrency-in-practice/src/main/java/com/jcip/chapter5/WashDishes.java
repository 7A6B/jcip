package com.jcip.chapter5;

import com.jcip.chapter5.Dishes.Status;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class Dishes{
    public enum Status {DIRTY,WASHED, DRIED}
    private Status status = Status.DIRTY;
    private final int id;
    public Dishes(int id){
        this.id = id;
    }
    public void washing(){
        status = Status.WASHED;
    }
    public void drying(){
        status = Status.DRIED;
    }
    public Status getStatus(){
        return status;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Dishes "+id+" : "+status;
    }
}

class DishesQueue extends LinkedBlockingQueue<Dishes>{}

class Collector implements Runnable{
    private final DishesQueue washedQueue;
    private int count = 0;
    private final Random rand = new Random(47);
    public Collector(DishesQueue dishesQueue){
        this.washedQueue = dishesQueue;
    }

    @Override
    public void run() {

        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(1000 + rand.nextInt(500));
                Dishes d= new Dishes(count++);
                System.out.println("收盘子==>"+d);
                // 收盘子
                washedQueue.put(d);
            }
        } catch (InterruptedException e) {
            System.out.println("Collector interrupted");
        }
        System.out.println("盘子收集完毕");
    }
}

class Washer implements Runnable{
    private final DishesQueue washedQueue,dryQueue;
    public Washer(DishesQueue washed,DishesQueue dry){
        this.washedQueue = washed;
        this.dryQueue = dry;
    }
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Dishes dishes = washedQueue.take();
                dishes.washing();
                System.out.println("清洗盘子==>"+dishes);
                dryQueue.put(dishes);
            }
        } catch (InterruptedException e) {
            System.out.println("Washer interrupted");
        }
        System.out.println("清洗结束");
    }
}

class Dryer implements Runnable{
    private final DishesQueue dryQueue,finishedQueue;
    public Dryer(DishesQueue dry,DishesQueue finished){
        this.dryQueue = dry;
        this.finishedQueue = finished;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Dishes dish = dryQueue.take();
                dish.drying();
                System.out.println("烘干盘子==>"+dish);
                finishedQueue.put(dish);
            }
        } catch (InterruptedException e) {
            System.out.println("Dryer interrupted");
        }
        System.out.println("烘干结束");
    }
}
class Cook implements Runnable{

    private DishesQueue finishedQueue;
    private int counter = 0;
    public Cook(DishesQueue finishedQueue){
        this.finishedQueue = finishedQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Dishes dishes = finishedQueue.take();
                if (dishes.getId() != counter++ || dishes.getStatus() != Status.DRIED) {
                    System.out.println(">>>> Error: " + dishes);
                    System.exit(1);
                } else {
                    System.out.println("Chomp!干净消毒的盘子可以使用==> " + dishes);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Cook interrupted");
        }
        System.out.println("厨师使用烘干后的盘子");
    }
}
public class WashDishes {

    public static void main(String[] args) throws InterruptedException {
        DishesQueue dirtyQueue = new DishesQueue(), washedQueue = new DishesQueue(), finishedQueue = new DishesQueue();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Collector(dirtyQueue));
        exec.execute(new Washer(dirtyQueue, washedQueue));
        exec.execute(new Dryer(washedQueue, finishedQueue));
        exec.execute(new Cook(finishedQueue));
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
}
