package com.zzw.Message.Entiy;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "db_message_history")
public class HistoryMessage {
    @TableId(value = "message_id")
    Long messageId;
    Integer fromId;
    Integer toId;
    Integer ownerId;
}
