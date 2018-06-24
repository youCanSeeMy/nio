package day3.com.baizhi.rpc.server;
import day3.com.baizhi.rpc.client.MethodInvokeMeta;
import day3.com.baizhi.rpc.client.RPCClientHandler;
import io.netty.channel.*;

import java.lang.reflect.Method;

public class ServerHandler extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("错了："+cause.getMessage());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("======"  + msg);
        MethodInvokeMeta methodInvokeMeta = (MethodInvokeMeta)msg;
        Object[] args = methodInvokeMeta.getArgs();
        String method = methodInvokeMeta.getMethod();
        Class<?> aClass = methodInvokeMeta.getTargetInterface();
        Class<?>[] types = methodInvokeMeta.getParameterTypes();

        Class<?> aClass1 = Class.forName(aClass.getName() + "Impl");
        Method method1 = aClass1.getDeclaredMethod(method, types);
        Object o = aClass1.newInstance();
        Object o1 = method1.invoke(o, args);

        System.out.println(o1.toString());

        ChannelFuture channelFuture = ctx.writeAndFlush(o1);
        channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }
}