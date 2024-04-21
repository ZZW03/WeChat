package com.zzw.tcp.Mq.Listener;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.zzw.common.Const;
import com.zzw.common.proto.MessagePack;
import com.zzw.tcp.Mq.Listener.process.BaseProcess;
import com.zzw.tcp.Mq.Listener.process.ProcessFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = Const.MQ.MessageService2Im,durable = "true"),
                exchange = @Exchange(value = Const.MQ.MessageService2Im,durable = "true")
        ),concurrency = "1"
)
public class MessageService2ImQueue {

        @RabbitHandler
        public void onChatMessage(String msg, org.springframework.amqp.core.Message MainMessage, Channel channel){
                    long deliveryTag = MainMessage.getMessageProperties().getDeliveryTag();
                    try{

                        String msgStr = String.valueOf(msg);
                        MessagePack messagePack =
                                JSONObject.parseObject(msgStr, MessagePack.class);
                        JSONObject data = JSON.parseObject(msg);
                        messagePack.setData(data);
                        BaseProcess messageProcess = ProcessFactory
                                .getMessageProcess(messagePack.getCommand());

                        messageProcess.process(messagePack);

                        //处理完信息
                        //如何我不写确认 那么
                        //如果没ack 那么就不会basicPublish
                        channel.basicAck(deliveryTag, false);


                    }catch (Exception e){
                        e.printStackTrace();
                    }

        }

}
