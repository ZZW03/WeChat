package com.zzw.Message.Service;

import com.zzw.Mq.Send.MessageProducer;
import com.zzw.common.model.RestBean;
import com.zzw.common.model.dto.MessageContent;
import com.zzw.common.model.enums.MessageError;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class P2PMessageService {

    @Resource
    MessageProducer messageProducer;


    public String SendMessage(MessageContent messageContent,Integer id){
        messageContent.setFromId(id);
        if(!messageProducer.sendToUser(messageContent)){
            return RestBean.error(MessageError.MESSAGE_SEND_ERROR.getCode(),MessageError.MESSAGE_SEND_ERROR.getError()).ToJSON();
        }
        return null;
    }

}
