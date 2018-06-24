package day2.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.List;

public class ObjectMessageToMessageEncoder extends MessageToMessageEncoder<Object> {
    /*
    编码器，将对象转化为byte对象
    ChannelHandlerContext,通道处理者工厂，允许同一个pipeline中，
    本channelHandler与其他channelHandler进行交互
    Object o是需要编码的对象
    List<Object> list是数据帧
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, List<Object> list) throws Exception {
        System.out.println("编码开始");
        /*channelHandlerContext.alloc()通过通道处理工厂调用
        alloc（）得到缓冲流分配器ByteBufAllocator，
        从而得到一个新的缓冲.
         */
        ByteBuf buffer = channelHandlerContext.alloc().buffer();
        /*SerializationUtils.serialize(...)将一个序列化的对象
        转换为byte数组
         */
        buffer.writeBytes(SerializationUtils.serialize((Serializable)o));
        list.add(buffer);
    }
}
