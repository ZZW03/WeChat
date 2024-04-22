package com.zzw.Friend.Mq.Listen;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.zzw.Account.Service.AccountDetailService;
import com.zzw.Friend.Mq.Send.FriendMqOperation;
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
import org.springframework.beans.factory.annotation.Value;
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

    @Resource
    FriendMqOperation friendMqOperation;

    @Value("${spring.rabbitmq.listener.simple.retry.max-attempts}")
    Integer value ;


    @RabbitHandler
    public void onChatMessage(String msg, org.springframework.amqp.core.Message MainMessage, Channel channel) throws IOException {
        long deliveryTag = MainMessage.getMessageProperties().getDeliveryTag();
        try {

            //todo 做好友添加的业务
            String msgStr = String.valueOf(msg);
            MessagePack messagePack = JSONObject.parseObject(msgStr, MessagePack.class);
            Integer command = messagePack.getCommand();

            // 解析data里面的数据
            JSONObject json = (JSONObject) JSON.toJSON(messagePack.getData());

            if (command.equals(FriendshipEventCommand.FRIEND_REQUEST.getCommand())) {
                Integer fromId = messagePack.getUserId();
                Integer toId = messagePack.getToId();
                String wording = (String) json.get("wording");
                AddFriendReq addFriendReq = new AddFriendReq(toId, wording);
                if (friendshipReqService.selOneReq(fromId, toId)) {
                    friendshipReqService.AddReq(fromId, addFriendReq);
                } else {
                    friendshipReqService.UpdateReaded(fromId, toId);
                }

                // todo 回一个ack信号
                friendMqOperation.ack(messagePack);

                log.info("完成添加好友业务");


            }if(command.equals(FriendshipEventCommand.FRIEND_ADD.getCommand())){
                Integer fromId = messagePack.getUserId();
                Integer toId = messagePack.getToId();
                Integer reqId = (Integer) json.get("wording");
                friendshipService.AddFriend(fromId, toId, reqId);
                log.info("添加好友业务完成");
                // todo 回一个ack信号 告诉客户端已经添加完成了
                friendMqOperation.ack(messagePack);
            }

            //主动回ack
            channel.basicAck(deliveryTag, false);


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

