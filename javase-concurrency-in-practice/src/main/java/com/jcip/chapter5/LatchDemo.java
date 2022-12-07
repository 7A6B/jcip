package com.jcip.chapter5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class Player implements Runnable{

    protected  final CountDownLatch startSingal;
    Player(CountDownLatch latch){
        startSingal = latch;
    }

    @Override
    public void run() {
        try {
            if(startSingal.getCount() == 0){
                System.err.println("游戏人数已满......无法进入游戏");
                return;
            }
            System.out.println(Thread.currentThread().getName()+"==>进入游戏，准备就绪");
            startSingal.await();
            play();//表示所有人员都已经准备好之后进行的动作
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void play() {
        System.out.println("人数已凑够，开始游戏");
    }
}
class Game{
    private final int num;//一局游戏中需要的人数
    private final CountDownLatch startSingal;
    Game(int num){
        this.num = num;
        startSingal = new CountDownLatch(num);//num表示将的调用num次countDown之后，await就可以放行了
    }
    void begin(int nplayers){
        for (int i = 0; i < nplayers; i++) {
            new Thread(new Player(startSingal)).start();

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            startSingal.countDown();
        }
    }
}
public class LatchDemo {

    public static void main(String[] args) {
        final Game game = new Game(2);//一局两个人
        game.begin(1);//表示1个人已经准备开始游戏了
        game.begin(1);
        game.begin(1);
    }
}
