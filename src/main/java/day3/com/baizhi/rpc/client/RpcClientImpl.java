package day3.com.baizhi.rpc.client;
import day3.com.baizhi.rpc.server.HostAndPort;
import day3.com.baizhi.rpc.server.Result;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class RpcClientImpl implements RpcClient {
    @Override
    public Result call(MethodInvokeMeta mim, HostAndPort hap) {
        ClientChannelInitializer clientChannelInitializer = new ClientChannelInitializer(mim);
        //1.创建启动引导
        Bootstrap bt=new Bootstrap();
        //2.配置线程池组 boss worker
        EventLoopGroup work=new NioEventLoopGroup();
        //3.配置线程池组
        bt.group(work);
        //4.设置服务器实现
        bt.channel(NioSocketChannel.class);
        //5.初始化通信管道
        bt.handler(clientChannelInitializer);
        try {
            //6.绑定端口启动服务
            ChannelFuture channelFuture = bt.connect(hap.getHost(),hap.getPort());
            //7.关闭SocketChannel
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //8.关闭线程资源
        work.shutdownGracefully();
        return clientChannelInitializer.getResult();
    }
}