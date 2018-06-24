package day4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.apache.commons.lang3.SerializationUtils;

import java.nio.ByteBuffer;
import java.util.List;

public class ObjectMessageToMessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("解码");
        byte[] bytes=new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);//将byteBuf数据写入字节数组
        Object o = SerializationUtils.deserialize(bytes);
        list.add(o);
    }
}
