package com.zzw.Friend.Mq.Listen;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.rabbitmq.client.Channel;
import com.zzw.Account.Service.AccountDetailService;
import com.zzw.Friend.Service.FriendshipReqService;
import com.zzw.Friend.Service.FriendshipService;
import com.zzw.common.Const;
import com.zzw.common.model.enums.Command.FriendshipEventCommand;
import com.zzw.common.model.req.friend.AddFriendReq;
import com.zzw.common.pack.LoginPack;
import com.zzw.common.proto.Message;
import com.zzw.common.proto.MessagePack;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = Const.MQ.Im2FriendshipService,durable = "true"),
                exchange = @Exchange(value = Const.MQ.Im2FriendshipService,durable = "true")
        ),concurrency = "1")
public class FriendMqListen {

    @Resource
    AmqpTemplate amqpTemplate;

    @Resource
    AccountDetailService accountService;

    @Resource
    FriendshipService friendshipService;

    @Resource
    FriendshipReqService friendshipReqService;

    @RabbitHandler
    public void onChatMessage(String msg){

        try{

            //todo 做好友添加的业务
            String msgStr = String.valueOf(msg);
            Message message =
                    JSONObject.parseObject(msgStr, Message.class);

            Integer command = message.getMessageHeader().getCommand();
            MessagePack messagePack = JSON.parseObject(JSONObject.toJSONString(message.getMessagePack()), new TypeReference<MessagePack>() {
            }.getType());

            // 解析data里面的数据
            JSONObject  json = (JSONObject) JSON.toJSON(messagePack.getData());

            if(command.equals(FriendshipEventCommand.FRIEND_ADD.getCommand())){
                //todo 做好友添加的业务

                Integer fromId = messagePack.getUserId();
                Integer toId = messagePack.getToId();
                String wording = (String) json.get("wording");
                AddFriendReq addFriendReq = new AddFriendReq(toId, wording);
                if(friendshipReqService.selOneReq(fromId,toId)){

                    friendshipReqService.AddReq(fromId, addFriendReq);
                }else{
                    friendshipReqService.UpdateReaded(fromId,toId);
                }


            }


//            amqpTemplate.convertAndSend(Const.MQ.MessageService2Im,messagebody);

        }catch (Exception e){
            log.error(e.getMessage());
        }

    }

}
