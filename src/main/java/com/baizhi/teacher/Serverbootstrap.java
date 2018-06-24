package com.baizhi.teacher;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Serverbootstrap {
    public static void main(String[] args) throws IOException {
        server01();
    }

    public static void server01() throws IOException {
//        1.	创建ServerSocket ss=new ServerSocket();
        ServerSocketChannel ss=ServerSocketChannel.open();
//        2.	绑定监听端口ss.bind(new InetSocketAddress(9999));
       // ss.bind(new InetSocketAddress(9999));
        ss.socket().bind(new InetSocketAddress(9999));
//        3.	等待客户端请求Socket s=ss.accept();(req,res)
        System.out.println("等待请求到来...");
        SocketChannel s = ss.accept();
        System.out.println("处理请求...");
//        4.	使用流操作请求和响应 套路IO
        //①读请求
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        while(true){
            buffer.clear();
            int n = s.read(buffer);
            if(n==-1) break;
            buffer.flip();
            baos.write(buffer.array(),0,n);
        }

        System.out.println("服务器收到："+new String(baos.toByteArray()));
        //②写响应
        String res=new Date().toLocaleString();
       s.write(ByteBuffer.wrap(res.getBytes()));
        s.socket().shutdownOutput();//告知客户端服务器写完
//        5.	关闭socket资源
        s.close();
    }
}
