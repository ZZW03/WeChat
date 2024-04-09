package com.zzw.Utils;


import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class FlowUtil {

    @Resource
    StringRedisTemplate RedisTemplate;


    public boolean LimitOnceCheck(String key , int blockTime){
            if (Boolean.TRUE.equals(RedisTemplate.hasKey(key))){
                return false; //限流
            }else {
                RedisTemplate.opsForValue().set(key,"",blockTime, TimeUnit.MINUTES);
                return true;
            }
    }

    public boolean LimitCheck(String key,int blockTime,int frequency){
        if(Objects.equals(RedisTemplate.opsForValue().get(key), String.valueOf(frequency))){
            return false;
        }else {
            if (RedisTemplate.opsForValue().get(key) == null){
                RedisTemplate.opsForValue().set(key, String.valueOf(0),blockTime,TimeUnit.MINUTES);
            }else {
                RedisTemplate.opsForValue().increment(key);
            }
            return true;
        }
    }
}
