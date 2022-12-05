package com.jcip.chapter5.crawer;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;

public class FileCrawler implements Runnable{
    private final BlockingQueue<File> fileQueue;
    private final FileFilter fileFilter;
    private ConcurrentSkipListSet indexedFiles = new ConcurrentSkipListSet();
    private final File root;
    FileCrawler(BlockingQueue<File> fileQueue,FileFilter fileFilter,File root){
        this.fileQueue = fileQueue;
        this.fileFilter = fileFilter;
        this.root = root;
    }

    @Override
    public void run() {
        try {
            crawl(root);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void crawl(File root) throws InterruptedException {
        final File[] entries = root.listFiles(fileFilter);
        if(entries!=null){
            for (File entry : entries) {
                if(entry.isDirectory()){
                    crawl(entry);
                }else if(!alreadyIndexed(entry)){
                    fileQueue.put(entry);
                }
            }
        }
    }

    private boolean alreadyIndexed(File entry) {
        return indexedFiles.contains(entry);
    }
}
