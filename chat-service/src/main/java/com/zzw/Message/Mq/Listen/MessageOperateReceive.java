package com.zzw.Message.Mq.Listen;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.zzw.Account.Service.AccountDetailService;
import com.zzw.common.Const;
import com.zzw.common.proto.Message;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = Const.MQ.Im2MessageService,durable = "true"),
                exchange = @Exchange(value = Const.MQ.Im2MessageService,durable = "true")
        ),concurrency = "1")
//交换机就是文件夹
//队列是文件
public class MessageOperateReceive {

    @Resource
    AmqpTemplate amqpTemplate;

    @Resource
    AccountDetailService accountService;

    @RabbitHandler
    public void onChatMessage(String msg){

        try{
            String msgStr = String.valueOf(msg);
            Message Message =
                    JSONObject.parseObject(msgStr, Message.class);

            JSONObject  json = (JSONObject) JSON.toJSON(Message.getMessagePack());
            Integer id = (Integer) json.get("userId");
            String nickname = accountService.SelName(id);
            JSONObject name = JSON.parseObject(nickname);
            String endName = (String) name.get("data");
            json.put("nickName",endName);
            String messagebody = JSON.toJSONString(json);
            amqpTemplate.convertAndSend(Const.MQ.MessageService2Im,messagebody);

        }catch (Exception e){
            log.error(e.getMessage());
        }

    }

}
