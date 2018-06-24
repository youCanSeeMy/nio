package day2.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import java.util.Date;

public class SeverChannelHandlerApater extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("错了"+cause.getMessage());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        System.out.println("服务器收到："+buf.toString(CharsetUtil.UTF_8));
        ByteBuf res = Unpooled.buffer();
        res.writeBytes(new Date().toLocaleString().getBytes());
        ChannelFuture channelFuture = ctx.writeAndFlush(res);
        //关闭通道
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }
}
