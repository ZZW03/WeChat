package com.zzw.tcp.configuation;

import com.zzw.tcp.handel.NettyHandlerService;
import com.zzw.tcp.utils.SocketHolder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class BasicConfiguration {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    SocketHolder socketHolder;

    @PostConstruct
    public void init(){
        NettyHandlerService.setStringRedisTemplate(stringRedisTemplate);
        NettyHandlerService.setSocketHolder(socketHolder);
    }
}
