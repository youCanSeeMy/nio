package day3.com.baizhi.rpc.server;

import day2.teacher.netty.ServerChannelHandlerApater;
import day4.ObjectMessageToMessageDecoder;
import day4.ObjectMessageToMessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
        public void initChannel(SocketChannel socketChannel) throws Exception {
            ChannelPipeline pipeline = socketChannel.pipeline();
            pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
            pipeline.addLast(new ObjectMessageToMessageDecoder());
            pipeline.addLast(new LengthFieldPrepender(2));
            pipeline.addLast(new ObjectMessageToMessageEncoder());
            pipeline.addLast(new ServerHandler());
        }
    }