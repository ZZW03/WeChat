package com.zzw.Message.Controller;

import com.zzw.Message.Dao.MessageMapper;
import com.zzw.Message.Service.ConversationSessionService;
import com.zzw.Message.Service.P2PMessageService;
import com.zzw.common.Const;
import com.zzw.common.model.RestBean;
import com.zzw.common.model.dto.MessageContent;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    MessageMapper messageMapper;

    @Resource
    P2PMessageService p2PMessageService;

    @Resource
    ConversationSessionService conversationSessionService;

    @PostMapping("/sendmessage")
    public String sendMessage(@RequestAttribute(Const.ACCOUNT.ATTR_USER_ID) Integer id,
                              @RequestBody MessageContent messageContent){

        return  p2PMessageService.SendMessage(messageContent,id);
    }

    @GetMapping("/getsession")
    public String GetAllSession(@RequestAttribute(Const.ACCOUNT.ATTR_USER_ID)Integer id){
        return conversationSessionService.GetAllSession(id);
    }

    @GetMapping("/getOneSessionMessage")
    public String getOneSessionMessage(Integer SessionId){
        return RestBean.success(messageMapper.SelOneMessageInSession(SessionId)).ToJSON();
    }
}
