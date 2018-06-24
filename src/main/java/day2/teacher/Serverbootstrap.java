package day2.teacher;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class Serverbootstrap {
    private static List<SelectionKey> writeQueue=new Vector<SelectionKey>();
    public static void main(String[] args) throws IOException {
        server01();
    }

    public static void server01() throws IOException {
        //创建ServerSocketChannel
        ServerSocketChannel ss=ServerSocketChannel.open();
        //绑定监听端口
        ss.socket().bind(new InetSocketAddress(8888));
        //设置非阻塞
        ss.configureBlocking(false);
        //创建Selector
        final Selector selector=Selector.open();
        //注册通道事件，可以携带附件信息
        ss.register(selector,SelectionKey.OP_ACCEPT);

        while(true){
            System.out.println("等待事件处理...");
            int n = selector.select();
            if(n>0){
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = keys.iterator();
                while(keyIterator.hasNext()){
                   final SelectionKey key = keyIterator.next();
                    if(key.isAcceptable()){
                        System.out.println("处理accept");
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = channel.accept();

                        socketChannel.configureBlocking(false);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        socketChannel.register(selector,SelectionKey.OP_READ,baos);
                    }else if(key.isReadable()){
                        System.out.println("处理read");

                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteArrayOutputStream baos= (ByteArrayOutputStream) key.attachment();

                        ByteBuffer buffer=ByteBuffer.allocate(1024);
                        int num = socketChannel.read(buffer);
                        buffer.flip();
                        if(num==-1){
                            System.out.println("keycancle："+new String(baos.toByteArray()));
                             key.cancel();//取消读注册
                            new Thread(){
                                @Override
                                public void run() {
                                    writeQueue.add(key);
                                    selector.wakeup();
                                }
                            }.start();
                        }else {
                            baos.write(buffer.array(), 0, num);
                        }
                    }else if(key.isWritable()){
                        System.out.println("处理write");

                        key.cancel();//取消写注册
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteArrayOutputStream baos= (ByteArrayOutputStream) key.attachment();
                        System.out.println("服务器收到："+new String(baos.toByteArray()));

                        socketChannel.write(ByteBuffer.wrap((new Date().toLocaleString()).getBytes()));
                        socketChannel.socket().shutdownOutput();

                        socketChannel.close();
                    }
                   //移除事件队列  ！= 移除注册队列
                    keyIterator.remove();
                }
            }else{
                System.out.println("-----------");
                while(!writeQueue.isEmpty()){
                    SelectionKey key = writeQueue.remove(0);
                    key.channel().register(selector,SelectionKey.OP_WRITE,key.attachment());
                }
            }
        }

    }
}
