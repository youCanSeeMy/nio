package com.baizhi.nio;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestNio {
    public static void main(String[] args)throws Exception{
        //获得输入流，输出流
        FileInputStream inputStream = new FileInputStream("C:/1.jpg");
        FileOutputStream outputStream = new FileOutputStream("C:/lingpengxiaogoushi.jpg");
        //获得管道
        FileChannel inputStreamChannel = inputStream.getChannel();
        FileChannel outputStreamChannel = outputStream.getChannel();
        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //拷贝
        while (true){
            buffer.clear();
            int i = inputStreamChannel.read(buffer);
            if(i==-1) break;
            buffer.flip();
            outputStreamChannel.write(buffer);
        }
        inputStreamChannel.close();
        outputStreamChannel.close();
        inputStream.close();
        outputStream.close();
    }
}
