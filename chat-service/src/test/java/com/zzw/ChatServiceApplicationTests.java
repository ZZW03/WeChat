package com.zzw;

import com.zzw.Account.Service.AccountService;
import com.zzw.Friend.Service.FriendshipService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ChatServiceApplication.class})
class ChatServiceApplicationTests {

	@Resource
	FriendshipService friendshipService;

	@Resource
	AccountService accountService;

	@Test
	void contextLoads() {
		if (accountService.getById(2) == null){
			System.out.println(123);
		}
	}

}
