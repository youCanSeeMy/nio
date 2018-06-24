package com.baizhi.bio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TestClientBIO {
    public static void main(String[] args) throws IOException {
        //1.创建socket
        Socket socket = new Socket();
        //2.绑定监听端口
        socket.connect(new InetSocketAddress("localhost",3333));
        //3.发请求
        OutputStream rep = socket.getOutputStream();
        PrintWriter printWriter = new PrintWriter(rep);
        printWriter.print("服务器你好，我是客户端");
        printWriter.flush();
        socket.shutdownOutput();
        //4.读取服务器响应
        InputStream res = socket.getInputStream();
        InputStreamReader reader = new InputStreamReader(res);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while((line=bufferedReader.readLine())!=null){
            stringBuilder.append(line);
        }
        System.out.println("服务端对客户端说的悄悄话"+stringBuilder.toString());
        //5.关闭资源
        socket.close();
    }
}