package com.zzw.tcp.configuation;

import com.zzw.tcp.Mq.Send.MqMessageProducer;
import com.zzw.tcp.fegin.MessageClient;
import com.zzw.tcp.handel.NettyHandlerService;
import com.zzw.tcp.utils.SocketHolder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class BasicConfiguration {

    @Resource
    MqMessageProducer messageProducer;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    SocketHolder socketHolder;

    @Resource
    MessageClient messageClient;

    @PostConstruct
    public void init(){
        NettyHandlerService.setStringRedisTemplate(stringRedisTemplate);
        NettyHandlerService.setSocketHolder(socketHolder);
        NettyHandlerService.setMessageProducer(messageProducer);
        NettyHandlerService.setMessageClient(messageClient);
    }
}
