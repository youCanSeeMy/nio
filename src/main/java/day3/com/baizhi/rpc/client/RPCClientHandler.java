package day3.com.baizhi.rpc.client;

import day3.com.baizhi.rpc.server.Result;
import day4.Close;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;

public class RPCClientHandler extends ChannelHandlerAdapter {
    /*MethodInvokeMeta通信协议，客户端向服务端发起请求的协议
       Result调用方法后，返回的结果
     */
    private MethodInvokeMeta methodInvokeMeta;
    private Result result = new Result();

    public MethodInvokeMeta getMethodInvokeMeta() {
        return methodInvokeMeta;
    }

    public void setMethodInvokeMeta(MethodInvokeMeta methodInvokeMeta) {
        this.methodInvokeMeta = methodInvokeMeta;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public RPCClientHandler(MethodInvokeMeta methodInvokeMeta) {
        this.methodInvokeMeta = methodInvokeMeta;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        result.setException((RuntimeException) cause.getCause());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelFuture channelFuture = ctx.writeAndFlush(methodInvokeMeta);
        channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
//        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("客户端收到：" + msg);
        result.setReturnValue(msg);
    }
}