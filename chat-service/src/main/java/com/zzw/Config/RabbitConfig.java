package com.zzw.Config;

import com.zzw.common.Const;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitConfig {

    @Bean("emailQueue")
    public Queue emailQueue() {
        return  QueueBuilder
                .durable(Const.MQ.MQ_QUEUE_NAME_MAIL)
                .build();
    }
}
