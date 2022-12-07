package com.jcip.chapter2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
class ExpensiveObject{
    ExpensiveObject(){
        System.out.println(this);
    }
}
public class LazyInitRace {
    private ExpensiveObject instance = null;
    public ExpensiveObject getInstance(){
        if(instance==null){
            instance = new ExpensiveObject();
        }
        return instance;
    }

    public static void main(String[] args) {
        final LazyInitRace initRace = new LazyInitRace();
        final ExecutorService executorService = Executors.newCachedThreadPool();
        //只会展示出构建时的输出，输出几个都代表并发时创建了多少个实例
        for (int i = 0; i < 10; i++) {
            executorService.execute(initRace::getInstance);
        }
        executorService.shutdown();
    }
}