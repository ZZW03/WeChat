package com.zzw.Friend.Mq.Send;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.zzw.common.Const;
import com.zzw.common.model.dto.MessageContent;
import com.zzw.common.model.enums.Command.FriendshipEventCommand;
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
            log.info("friendshipservice send message == " + msg);
            amqpTemplate.convertAndSend(Const.MQ.FriendshipService2Im,msg);
            return true;
        }catch (Exception e){
            log.error("friendshipservice send error :" + e.getMessage());
            return false;
        }
    }

    public void ack(MessagePack<Object> messagePack){
        JSONObject jsonObject= (JSONObject) JSON.toJSON(messagePack);
        Integer command = (Integer) jsonObject.get("command");
        Integer toId = (Integer) jsonObject.get("toId");
        Integer foId = (Integer) jsonObject.get("userId");
        messagePack.setToId(foId);
        messagePack.setUserId(toId);
        if(command.equals(FriendshipEventCommand.FRIEND_REQUEST.getCommand())){
            messagePack.setData("发送成功");
        } else if (command.equals(FriendshipEventCommand.FRIEND_ADD.getCommand())){
            messagePack.setData("添加成功");
        }
        String msg = JSONObject.toJSONString(messagePack);
        if(this.sendMessage(msg)){
            log.info("回复成功");
        }else {
            log.info("回复失败 ， 系统出现问题");
        }
    }
}
