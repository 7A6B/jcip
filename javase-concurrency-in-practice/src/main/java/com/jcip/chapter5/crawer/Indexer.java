package com.jcip.chapter5.crawer;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class Indexer implements Runnable{

    private BlockingQueue<File> queue;
    public Indexer(BlockingQueue<File> queue){
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            while (true) {
                indexFile(queue.take());
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    private void indexFile(File file) {
        System.out.println("Indexing..."+file);
    }
}
