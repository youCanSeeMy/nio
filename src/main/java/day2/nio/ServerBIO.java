package day2.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ServerBIO {
    public static void main(String[] args) throws IOException {
        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //绑定监听端口
        serverSocketChannel.bind(new InetSocketAddress(9999));
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //创建selector
        Selector selector = Selector.open();
        //注册通道事件，可以携带附件信息
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        while(true){
            System.out.println("等待事件被处理");
            int n=selector.select();
            if(n>0){
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> key = selectionKeys.iterator();
                while (key.hasNext()){
                    SelectionKey selectionKey = key.next();
                    if(selectionKey.isAcceptable()){
                        System.out.println("处理accept");
                        ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel)selectionKey.channel();
                        SocketChannel socketChannel = serverSocketChannel1.accept();
                        socketChannel.configureBlocking(false);
                    }

                }

            }

        }












    }
}
