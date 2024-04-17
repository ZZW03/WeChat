package com.zzw;

import com.zzw.common.model.req.friend.CheckStatusReq;
import com.zzw.tcp.TcpServiceApplication;
import com.zzw.tcp.fegin.MessageClient;
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

	@Autowired(required = true)
	MessageClient messageClient;

	@Test
	void contextLoads() {
		CheckStatusReq checkStatusReq = new CheckStatusReq(1,2);
		System.out.println(messageClient.CheckStatus(checkStatusReq));
	}

}
