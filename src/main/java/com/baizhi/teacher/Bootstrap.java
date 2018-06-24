package com.baizhi.teacher;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class Bootstrap {
    public static void main(String[] args) throws IOException {
//        1.	创建Socket s=new Socket();
        SocketChannel s=SocketChannel.open();
//        2.	连接服务器s.connect(new InetSocketAddress(ip,9999));
        s.connect(new InetSocketAddress("localhost",9999));
//        3.	使用流操作请求和响应 套路IO
        //①发请求
        String res="你好，我是客户端NIO";
        s.write(ByteBuffer.wrap(res.getBytes()));
        s.socket().shutdownOutput();//告知服务请求内容发送结束
        //②读取服务器响应
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        while(true){
            buffer.clear();
            int n = s.read(buffer);
            if(n==-1) break;
            buffer.flip();
            baos.write(buffer.array(),0,n);
        }

        System.out.println("客户端收到："+new String(baos.toByteArray()));

//        4.	关闭socket资源
        s.close();
    }
}
