package com.zzw.Utils;

import com.zzw.common.Const;
import com.zzw.common.model.UserSession;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserSessionUtil {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    public UserSession getUserSession(Integer id){
        return (UserSession)  stringRedisTemplate.opsForHash().get(Const.REDIS.USER_SESSION,id);
    }

}
