package com.zzw.Friend.Mq.Listen;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.rabbitmq.client.AMQP;
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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = Const.MQ.Im2FriendshipService, durable = "true"),
                exchange = @Exchange(value = Const.MQ.Im2FriendshipService, durable = "true")
        ), concurrency = "1")
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
    public void onChatMessage(String msg, org.springframework.amqp.core.Message MainMessage, Channel channel) throws IOException {
        long deliveryTag = MainMessage.getMessageProperties().getDeliveryTag();
        try {


            //todo 做好友添加的业务
            String msgStr = String.valueOf(msg);
            Message message = JSONObject.parseObject(msgStr, Message.class);

            Integer command = message.getMessageHeader().getCommand();
            MessagePack messagePack = JSON.parseObject(JSONObject.toJSONString(message.getMessagePack()), new TypeReference<MessagePack>() {
            }.getType());

            // 解析data里面的数据
            JSONObject json = (JSONObject) JSON.toJSON(messagePack.getData());

            if (command.equals(FriendshipEventCommand.FRIEND_ADD.getCommand())) {
                //todo 做好友添加的业务

                Integer fromId = messagePack.getUserId();
                Integer toId = messagePack.getToId();
                String wording = (String) json.get("wording");
                AddFriendReq addFriendReq = new AddFriendReq(toId, wording);
                if (friendshipReqService.selOneReq(fromId, toId)) {

                    friendshipReqService.AddReq(fromId, addFriendReq);
                } else {
                    friendshipReqService.UpdateReaded(fromId, toId);
                }
                log.info("完成添加好友业务");
            }

            //发送消息 和 回应消息的CorrelationId 一致才能说明是回应刚刚那条消息
            AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(MainMessage.getMessageProperties().getCorrelationId())  // 设置 correlationId
                    .build();

            String replyMessage = "已经被消费"; // 设置你要发送的回复消息内容

            //处理完信息
            //如何我不写确认 那么
            //如果没ack 那么就不会basicPublish
            channel.basicAck(deliveryTag, false);
            channel.basicPublish("", MainMessage.getMessageProperties().getReplyTo(), replyProps, replyMessage.getBytes());

        } catch (Exception e) {
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

