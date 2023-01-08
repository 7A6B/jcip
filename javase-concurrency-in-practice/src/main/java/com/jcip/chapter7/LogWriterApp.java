package com.jcip.chapter7;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogWriterApp {
    private static final PrintWriter writer;

    static {
        try{
            writer = new PrintWriter(new FileWriter("log.txt"));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    private static final LogWriter log = new LogWriter(writer);

    static void testLog() throws InterruptedException{
        log.log("testLog().....log");
    }

    static void foo() throws InterruptedException{
        log.log("foo() ......log");
    }

    public static void main(String[] args) throws InterruptedException {
        testLog();
        foo();
        log.start();
    }
}
