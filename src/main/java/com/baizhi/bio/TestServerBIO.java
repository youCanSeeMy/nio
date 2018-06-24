package com.baizhi.bio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestServerBIO {
    public static void main(String[] args) throws IOException {
        //1.创建ServerSocket
        ServerSocket socket = new ServerSocket();
        //2.绑定监听端口
        socket.bind(new InetSocketAddress(3333));
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        while (true){
            //3.接收客户端请求
            System.out.println("wait request........");
            final Socket socket1 = socket.accept();
            System.out.println("make request......");
            //4.使用流操作请求和响应
            //4-1.读请求
            //4-1-1.得到输入流
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    try{
                        InputStream req = socket1.getInputStream();
                        //4-1-2.字节流----》字符流
                        Reader reader = new InputStreamReader(req);
                        //4-1-3包装
                        BufferedReader bufferedReader = new BufferedReader(reader);
                        //4-1-4读取流
                        StringBuilder stringBuilder = new StringBuilder();
                        String line = null;
                        while ((line=bufferedReader.readLine())!=null){
                            stringBuilder.append(line);
                        }
                        System.out.println("服务器接收到："+stringBuilder);
                        //4-2.写响应
                        OutputStream res =socket1.getOutputStream();
                        PrintWriter printWriter = new PrintWriter(res);
                        printWriter.print("time"+new Date());
                        printWriter.flush();
                        //4-2-1.告知客户端服务器已经写完
                        socket1.shutdownOutput();
                        //5.关闭资源
                        socket1.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
        }
    }
}
