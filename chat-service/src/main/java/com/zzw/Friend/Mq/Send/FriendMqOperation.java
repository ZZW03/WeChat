package com.zzw.Friend.Mq.Send;

import com.alibaba.fastjson2.JSONObject;
import com.zzw.common.Const;
import com.zzw.common.model.dto.MessageContent;
import com.zzw.common.proto.MessagePack;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FriendMqOperation {

    @Resource
    AmqpTemplate amqpTemplate;


    public boolean sendMessage(String msg){
        try {
            log.info("send message == " + msg);
            amqpTemplate.convertAndSend(Const.MQ.MessageService2Im,msg);
            return true;
        }catch (Exception e){
            log.error("send error :" + e.getMessage());
            return false;
        }
    }

    public Boolean sendToUser(MessageContent messageContent){
        MessagePack<Object> messagePack = new MessagePack<>();
        BeanUtils.copyProperties(messageContent,messagePack);
        messagePack.setData(JSONObject.toJSONString(messageContent));
        String body = JSONObject.toJSONString(messagePack);
        return sendMessage(body);
    }
}
