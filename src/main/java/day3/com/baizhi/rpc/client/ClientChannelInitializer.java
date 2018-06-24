package day3.com.baizhi.rpc.client;

import day3.com.baizhi.rpc.server.Result;
import day4.ObjectMessageToMessageDecoder;
import day4.ObjectMessageToMessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    private MethodInvokeMeta methodInvokeMeta;
    private RPCClientHandler rpcClientHandler;
    public ClientChannelInitializer(MethodInvokeMeta methodInvokeMeta) {
        this.methodInvokeMeta = methodInvokeMeta;
        this.rpcClientHandler = new RPCClientHandler(methodInvokeMeta);
    }

    public Result getResult() {
        return rpcClientHandler.getResult();
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
        pipeline.addLast(new ObjectMessageToMessageDecoder());
        pipeline.addLast(new LengthFieldPrepender(2));
        pipeline.addLast(new ObjectMessageToMessageEncoder());//编码
        pipeline.addLast(rpcClientHandler);
    }
}
