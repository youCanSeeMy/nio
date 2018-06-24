package day2.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //1.创建服务器启动引导
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //2.配置线程池组boss worker
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        //3.配置线程池组
        serverBootstrap.group(boss,worker);
        //4.设置服务器实现
        serverBootstrap.channel(NioServerSocketChannel.class);
        //5.初始化通信管道
        serverBootstrap.childHandler(new ServerChannelInitializer());
        //6.绑定端口启动服务
        System.out.println("服务器启动了");
        ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();
        //7.关闭SocketChannel
        channelFuture.channel().closeFuture().sync();
    }
}
