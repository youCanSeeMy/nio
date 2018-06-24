package com.baizhi.bio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientBIO {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost",7777));

        OutputStream outputStream = socket.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        PrintWriter printWriter = new PrintWriter(outputStreamWriter);
        printWriter.println("我是小小滴客户端");
        printWriter.flush();
        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = null;
        StringBuilder builder = new StringBuilder();
        while((line=reader.readLine())!=null){
            builder.append(line);
        }
        System.out.println(builder.toString()+"这是客户端接收的话");

        socket.close();
    }
}
