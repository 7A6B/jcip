package com.jcip.chapter8;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDeadLock {
    ExecutorService exec = Executors.newSingleThreadExecutor();
    public class LoadFileTask implements Callable<String>{
        private final String fileName;
        public LoadFileTask(String fileName){
            this.fileName = fileName;
        }

        @Override
        public String call() throws Exception {
            return " ";
        }
    }
    public class RenderPageTask implements Callable<String>{

        @Override
        public String call() throws Exception {
            Future<String> header,footer;
            header = exec.submit(new LoadFileTask("header.html"));
            footer = exec.submit(new LoadFileTask("footer.html"));
            String page = renderBody();
            return header.get()+ page + footer.get();
        }

        private String renderBody() {
            return "";
        }
    }
    public void test(){
        //我在等待页眉、页脚返回页面内容才能渲染；但是页面、页脚的任务再等待你释放线程才能去执行
        exec.submit(new RenderPageTask());
    }

    public static void main(String[] args) {
        final ThreadDeadLock threadDeadLock = new ThreadDeadLock();
        threadDeadLock.test();
    }
}
