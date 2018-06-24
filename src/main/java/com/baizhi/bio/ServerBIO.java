package com.baizhi.bio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerBIO {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(7777));
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        while (true) {
            final Socket socket = serverSocket.accept();


            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream request = socket.getInputStream();
                        InputStreamReader reader = new InputStreamReader(request);
                        BufferedReader bufferedReader = new BufferedReader(reader);
                        String line = null;
                        StringBuilder builder = new StringBuilder();
                        while ((line = bufferedReader.readLine()) != null) {
                            builder.append(line);
                        }
                        System.out.println(builder + "这是服务端接收的话");

                        OutputStream outputStream = socket.getOutputStream();
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                        PrintWriter printWriter = new PrintWriter(outputStreamWriter);
                        printWriter.print("hello,我是服务器");
                        printWriter.flush();
                        socket.shutdownOutput();
                        socket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }

    }
}


