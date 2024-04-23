package com.zzw.Message.Entiy;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "db_message")
public class Message {
    @TableId(value = "message_id")
    Integer messageId;
    String messageBody;
    Integer messageDelflat = 0;
    String messageSendTime = String.valueOf(new Date());
}
