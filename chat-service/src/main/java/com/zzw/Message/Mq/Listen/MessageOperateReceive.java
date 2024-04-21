package com.zzw.Message.Mq.Listen;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.zzw.Account.Service.AccountDetailService;
import com.zzw.common.Const;
import com.zzw.common.proto.Message;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

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

    @Value("${spring.rabbitmq.listener.simple.retry.max-attempts}")
    Integer value ;

    @Resource
    AmqpTemplate amqpTemplate;

    @Resource
    AccountDetailService accountService;

    @RabbitHandler
    public void onChatMessage(String msg, org.springframework.amqp.core.Message MainMessage, Channel channel) throws IOException {
        long deliveryTag = MainMessage.getMessageProperties().getDeliveryTag();
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
            channel.basicAck(deliveryTag, false);

            //处理完信息
            //如何我不写确认 那么
            //如果没ack 那么就不会basicPublish



        }catch (Exception e) {
            // 获取redis重试次数
            if (value < 2) {
                // 存入redis
                value += 1;
            } else if (value.equals(2) ) { // 如果第三次还是有异常，那么第三次的次数value值还是2 所以加入重试表
                // logic // 加入重试表
                log.error("消息[{}]消费失败...传递参数[{}]", MainMessage,msg);
                log.info("需要人工操作");
                // 签收失败并不重试
                channel.basicNack(MainMessage.getMessageProperties().getDeliveryTag(), false, false);
                return;
            }
            log.info("签收失败[{}]",msg);
            throw new RuntimeException("签收异常");
        }

    }

}
