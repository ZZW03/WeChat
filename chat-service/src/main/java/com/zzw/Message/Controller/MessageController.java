package com.zzw.Message.Controller;

import com.zzw.Message.Service.P2PMessageService;
import com.zzw.common.Const;
import com.zzw.common.model.dto.MessageContent;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    P2PMessageService p2PMessageService;

    @PostMapping("/sendmessage")
    public String sendMessage(@RequestAttribute(Const.ACCOUNT.ATTR_USER_ID) Integer id,
                              @RequestBody MessageContent messageContent){
        System.out.println(messageContent);
        return  p2PMessageService.SendMessage(messageContent,id);
    }
}
