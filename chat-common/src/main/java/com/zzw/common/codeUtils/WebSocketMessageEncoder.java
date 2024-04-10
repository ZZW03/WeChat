package com.zzw.common.codeUtils;



import com.alibaba.fastjson2.JSONObject;
import com.zzw.common.proto.MessagePack;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;



import java.util.List;


public class WebSocketMessageEncoder extends MessageToMessageEncoder<MessagePack> {


    @Override
    protected void encode(ChannelHandlerContext ctx, MessagePack msg, List<Object> out)  {

        try {
            System.out.println(456);
            String s = JSONObject.toJSONString(msg);
            ByteBuf byteBuf = Unpooled.directBuffer(8+s.length());
            byte[] bytes = s.getBytes();
            byteBuf.writeInt(msg.getCommand());
            byteBuf.writeInt(bytes.length);
            byteBuf.writeBytes(bytes);
            out.add(new BinaryWebSocketFrame(byteBuf));
        }catch (Exception e){
            System.out.println(123);
            e.printStackTrace();
        }

    }
}