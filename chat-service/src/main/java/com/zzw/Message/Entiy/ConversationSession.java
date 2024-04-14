package com.zzw.Message.Entiy;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("db_conversation_session")
public class ConversationSession {
    @TableId("session_id")
    Integer sessionId;
    Integer FromId;
    Integer ToId;
}
