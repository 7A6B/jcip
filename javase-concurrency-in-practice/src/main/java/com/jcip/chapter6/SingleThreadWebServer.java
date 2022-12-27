package com.jcip.chapter6;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadWebServer {

    public static void main(String[] args) throws IOException {
        try(ServerSocket socket = new ServerSocket(80)){
            System.out.println("服务器启动成功");
            while (true){
                final Socket connection = socket.accept();
                System.out.println(Thread.currentThread().getName()+"连接成功==>"+connection);
                handleRequest(connection);
            }
        }catch (IOException exception){
            System.out.println("服务器启动失败");
        }
    }

    private static void handleRequest(Socket connection) {
        try {
            final InputStream inputStream = connection.getInputStream();
            final byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf))!=-1){
                final String msg = new String(buf, 0, len);
                System.out.println("客户端发送来的消息位："+msg);
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
