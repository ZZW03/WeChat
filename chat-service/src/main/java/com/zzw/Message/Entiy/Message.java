package com.zzw.Message.Entiy;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "db_message")
public class Message {
    @TableId(value = "message_id")
    Integer messageId;
    String messageBody;
    String messageCreate;
    Integer messageDelflat;
    String messageSendTime;
}
