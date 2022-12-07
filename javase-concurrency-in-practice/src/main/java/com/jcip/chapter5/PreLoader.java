package com.jcip.chapter5;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class PreLoader {

    private final FutureTask<ProductInfo> futureTask = new FutureTask<>(this::loadProductInfo);

    private final Thread thread = new Thread(futureTask);

    public void start(){
        thread.start();
    }

    ProductInfo loadProductInfo(){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ProductInfo();
    }

    public ProductInfo get() throws DataLoadException, InterruptedException {
        try {
            return futureTask.get();
        }catch (ExecutionException e){
            final Throwable cause = e.getCause();
            if(cause instanceof DataLoadException){
                throw (DataLoadException) cause;//自定义异常处理
            }else {
              throw  launderThrowable(cause);//call()异常的分类处理
            }
        }
    }

    private RuntimeException launderThrowable(Throwable t){
        if(t instanceof RuntimeException){
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        }else {
            throw new IllegalStateException("Not unchecked",t);
        }
    }

    private static class DataLoadException extends Exception{};

    private static class ProductInfo{

        @Override
        public String toString() {
            return "ProductInfo";
        }
    }

    public static void main(String[] args) throws InterruptedException, DataLoadException {
        final PreLoader preLoader = new PreLoader();
        preLoader.start();
        final ProductInfo productInfo = preLoader.get();//此处会阻塞5秒钟
        System.out.println(productInfo);
    }
}
