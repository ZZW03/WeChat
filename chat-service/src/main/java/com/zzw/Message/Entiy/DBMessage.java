package com.zzw.Message.Entiy;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "db_message")
public class DBMessage {
    @TableId(value = "message_id")
    Long messageId;
    Object messageBody;

    Integer messageDelFlat = 0;
    String messageSendTime = String.valueOf(new Date());
}
