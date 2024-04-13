package com.zzw.tcp.utils;

import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class SocketHolder {

    public static Map<Integer, NioSocketChannel> CHANNELS = new ConcurrentHashMap<>();

    //在请求联机的时候 将chanel 存入到map中  便于之后查找
    public void put(Integer ID,
                     NioSocketChannel channel){
        CHANNELS.put(ID,channel);
    }

    //通过消息中的信息 ，来得到指定的channel
    public NioSocketChannel get(Integer ID){
        return CHANNELS.get(ID);
    }

    public void remove(Integer ID){
        CHANNELS.remove(ID);
    }
}
