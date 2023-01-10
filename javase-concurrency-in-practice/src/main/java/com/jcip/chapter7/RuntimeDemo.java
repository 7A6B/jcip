package com.jcip.chapter7;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Runtime封装Java应用运行时的环境。通过Runtime实例，使得应用程序和其运行环境相连接。
 * Runtime是在应用启动期间自动建立，应用程序不能够创建Runtime，
 * 但是我们可以通过Runtime.getRuntime()来获得当前应用的Runtime对象引用（应用中唯一），
 * 通过该引用我们可以获得当前运行环境的相关信息，比如空闲内存、最大内存以及为当前虚拟机添加关闭钩子（addShutdownHook()），
 * 执行指定命令（exec()）等等
 */
public class RuntimeDemo {

    static {
        final Runtime runtime = Runtime.getRuntime();
        //System.out.println("静态代码块中的实例==>"+runtime);
        runtime.addShutdownHook(new Thread(()->{
            System.out.println("这是静态代码块中的关闭钩子方法");
        }));
    }

    static void cleanUp(Runtime runtime){
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("休眠结束，执行关闭钩子方法......");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("这是方法参数中的runtime==>"+runtime);
        runtime.addShutdownHook(new Thread(()->{
            System.out.println("这是cleanUp()中的关闭钩子");
        }));

    }

    static void exec(Runtime runtime){
        try {
            runtime.exec("calc");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void memory(Runtime runtime){
        final long used = runtime.totalMemory() - runtime.freeMemory();
        long max = runtime.maxMemory();
        if(used / (double)max > 0.85){
            System.out.println("---内存占用过大，得清理工作----");
        }else {
            System.out.println("内存富裕");
        }
    }

    public static void main(String[] args) {
        final Runtime runtime = Runtime.getRuntime();
        //exec(runtime);
        //memory(runtime);
        cleanUp(runtime);
    }
}
