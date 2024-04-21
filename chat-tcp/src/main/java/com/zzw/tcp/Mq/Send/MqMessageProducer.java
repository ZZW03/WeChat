package com.zzw.tcp.Mq.Send;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.rabbitmq.client.Channel;
import com.zzw.common.Const;
import com.zzw.common.model.enums.Command.CommandType;
import com.zzw.common.proto.Message;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@Slf4j
public class MqMessageProducer {

    @Resource
    private AmqpTemplate amqpTemplate;

    @Resource
    private RabbitTemplate rabbitTemplate;

    public MqMessageProducer() {
    }


    public void sendMessage(Message message, Integer command) {


        Channel channel = null;
        String com = command.toString();
        String commandSub = com.substring(0, 1);
        CommandType commandType = CommandType.getCommandType(commandSub);
        String channelName = "";
        if (commandType == CommandType.MESSAGE) {
            channelName = Const.MQ.Im2MessageService;
        } else if (commandType == CommandType.GROUP) {
            channelName = Const.MQ.Im2GroupService;
        } else if (commandType == CommandType.FRIEND) {
            channelName = Const.MQ.Im2FriendshipService;
        } else if (commandType == CommandType.USER) {
            channelName = Const.MQ.Im2UserService;
        }

        // 设置关联消息 这样就知道哪条消息发送成功

        try {
            String MessageBody = JSON.toJSONString(message);
            rabbitTemplate.convertAndSend(channelName, "", MessageBody);

        } catch (Exception e) {
            log.error("发送消息出现异常：{}", e.getMessage());
        }
    }
}
