package com.zzw;

import com.zzw.tcp.TcpServiceApplication;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = {TcpServiceApplication.class})
class TcpServiceApplicationTests {

	@Resource
	StringRedisTemplate stringRedisTemplate;


	@Test
	void contextLoads() {
		stringRedisTemplate.opsForHash().put("1","2","123");
		stringRedisTemplate.opsForHash().put("1","3","123");
		System.out.println(stringRedisTemplate.opsForHash().get("1", "3"));
	}

}
