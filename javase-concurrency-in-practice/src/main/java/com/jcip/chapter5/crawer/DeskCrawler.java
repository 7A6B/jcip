package com.jcip.chapter5.crawer;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

public class DeskCrawler {

    public static void main(String[] args) {
        final LinkedBlockingQueue<File> blockingQueue = new LinkedBlockingQueue<>(1000);
        new Thread(new FileCrawler(blockingQueue, new FileFilter() {
            private final Pattern pattern = Pattern.compile(".*\\.pdf");
            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory()){
                    return true;
                }
                return pattern.matcher(new File(pathname.toURI()).getName()).matches();
            }
        }, new File("D:\\java\\ebook"))).start();

        new Thread(new Indexer(blockingQueue)).start();
        final ExecutorService executorService = Executors.newWorkStealingPool();
    }
}
