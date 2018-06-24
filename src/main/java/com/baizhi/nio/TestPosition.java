package com.baizhi.nio;
import java.nio.ByteBuffer;
public class TestPosition {
    public static void main(String[] args) {
        //创建ByteBuffer
        byte[] bytes = new byte[10];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        getMessage(buffer);
        //初始化
        buffer.put((byte)97);
        buffer.put((byte)63);
        getMessage(buffer);
        //读取
        buffer.position(0);
        buffer.limit(2);
        while(buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
        getMessage(buffer);
        //再次写入
        buffer.clear();
        buffer.put((byte)19);
        buffer.put((byte)20);
        //再次读取
        buffer.flip();
        while(buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }
    public static void getMessage(ByteBuffer buffer){
        System.out.println("现在位置是"+buffer.position()+"限度"+buffer.limit()+"容量"+buffer.capacity());
    }
}
