package com.zzw.common.utils;



import com.alibaba.fastjson2.JSONObject;
import com.zzw.common.proto.Message;
import com.zzw.common.proto.MessageHeader;
import io.netty.buffer.ByteBuf;



public class ByteBufToMessageUtils {

    public static Message transition(ByteBuf in){

        int command = in.readInt();

        int messageType = in.readInt();

        int bodyLen = in.readInt();

        if(in.readableBytes() < bodyLen ){
            in.resetReaderIndex();
            return null;
        }

        byte [] bodyData = new byte[bodyLen];
        in.readBytes(bodyData);


        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setCommand(command);
        messageHeader.setLength(bodyLen);
        messageHeader.setMessageType(messageType);

        Message message = new Message();
        message.setMessageHeader(messageHeader);

        if(messageType == 0x0){
            String body = new String(bodyData);
            JSONObject parse =  JSONObject.parse(body);
            message.setMessagePack(parse);
        }

        in.markReaderIndex();
        return message;
    }

}
