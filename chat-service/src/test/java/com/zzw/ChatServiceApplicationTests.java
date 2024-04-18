package com.zzw;

import com.zzw.Account.Service.AccountDetailService;
import com.zzw.Account.Service.AccountService;
import com.zzw.Friend.Service.FriendshipService;
import com.zzw.Message.Dao.MessageMapper;
import com.zzw.Message.Service.ConversationSessionService;
import com.zzw.Message.Service.P2PMessageService;
import com.zzw.common.model.dto.MessageContent;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ChatServiceApplication.class})
class ChatServiceApplicationTests {

	@Resource
	FriendshipService friendshipService;

	@Resource
	AccountService accountService;

	@Resource
	P2PMessageService p2PMessageService;

	@Resource
	AccountDetailService accountDetailService;

	@Resource
	ConversationSessionService conversationSessionService;

	@Resource
	MessageMapper messageMapper;

	@Test
	void contextLoads() {
		System.out.println(accountService.getIdByUserName("zzw"));

	}
}