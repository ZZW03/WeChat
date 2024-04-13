package com.zzw.tcp.Mq.Listener.process;


import com.zzw.common.proto.MessagePack;
import com.zzw.tcp.utils.SocketHolder;
import io.netty.channel.socket.nio.NioSocketChannel;


public abstract class BaseProcess {

    public abstract void processBefore();

    SocketHolder sessionSocketHolder = new SocketHolder();

    public void process(MessagePack messagePack){


        //解析信息
        processBefore();
        //从信息中得到要发送的channel
        NioSocketChannel channel = sessionSocketHolder.get(messagePack.getToId());

        //得到channel 后直接将信息发送给 指定客户端
        if(channel != null){

            channel.writeAndFlush(messagePack);
        }

        processAfter();
    }

    public abstract void processAfter();

}
