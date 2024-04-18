package com.zzw.Account.Mq.Listener;


import com.zzw.common.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = Const.MQ.Im2UserService,durable = "true"),
                exchange = @Exchange(value = Const.MQ.Im2UserService,durable = "true")
        ),concurrency = "1")
public class AccountMqListen {
}
