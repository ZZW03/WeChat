package com.zzw.common.codeUtils;



import com.zzw.common.proto.Message;
import com.zzw.common.utils.ByteBufToMessageUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


public class WebSocketMessageDecoder extends MessageToMessageDecoder<BinaryWebSocketFrame> {

        @Override
        protected void decode(ChannelHandlerContext ctx, BinaryWebSocketFrame msg, List<Object> out) throws Exception {

            ByteBuf content = msg.content();
            if (content.readableBytes() < 12) {
                return;
            }
            Message message = ByteBufToMessageUtils.transition(content);
            if(message == null){
                return;
            }
            out.add(message);
        }
}
