package day2.netty;

import day2.teacher.netty.ClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //1.创建启动引导
        Bootstrap bootstrap = new Bootstrap();
        //2.配置线程池组
        NioEventLoopGroup work = new NioEventLoopGroup();
        //3.配置线程池组
        bootstrap.group(work);
        //4.设置服务器实现
        bootstrap.channel(NioSocketChannel.class);
        //5.初始化通信管道
        bootstrap.handler(new ClientChannelInitializer());
        //6.绑定端口启动服务
        ChannelFuture channelFuture = bootstrap.connect("localhost", 9999).sync();
        //7.关闭SocketChannel
        channelFuture.channel().closeFuture().sync();
        //8.关闭线程资源
        work.shutdownGracefully();
    }
}
