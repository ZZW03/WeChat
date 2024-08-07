package com.zzw;

import com.zzw.Account.Service.AccountDetailService;
import com.zzw.Account.Service.AccountService;
import com.zzw.Account.Service.Impl.AccountServiceImpl;
import com.zzw.Friend.Service.FriendshipService;
import com.zzw.Friend.Service.impl.FriendshipServiceImpl;
import com.zzw.Message.Dao.ConversationSessionMapper;
import com.zzw.Message.Dao.MessageMapper;
import com.zzw.Message.Entiy.DBMessage;
import com.zzw.Message.Entiy.HistoryMessage;
import com.zzw.Message.Service.ConversationSessionService;
import com.zzw.Message.Service.P2PMessageService;
import com.zzw.Message.Service.StoreMessageService;
import com.zzw.common.model.dto.MessageContent;
import com.zzw.common.model.req.account.SendCodeReq;
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

	@Resource
	StoreMessageService storeMessageService;

	@Resource
	ConversationSessionMapper conversationSessionMapper;






	@Test
	void contextLoads() {
		SendCodeReq sendCodeReq = new SendCodeReq();
		sendCodeReq.setEmail("792144169@qq.com");
		sendCodeReq.setType("forget");
		accountService.SendCode(sendCodeReq,"123");
	}
}