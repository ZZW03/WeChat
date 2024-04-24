package com.zzw.Message.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzw.Message.Entiy.ConversationSession;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ConversationSessionMapper extends BaseMapper<ConversationSession> {

    @Insert("INSERT INTO db_conversation_session_body(session_id, message_id) VALUES (#{sessionId}, #{messageId})")
    public Integer insertMessage(@Param("messageId") Long messageId, @Param("sessionId") Integer sessionId);

    @Select("select session_id from db_conversation_session where from_id = #{fromId} and to_id = #{toId}")
    public Integer SelSessionId(@Param("fromId") Integer FromId,@Param("toId") Integer toId);
}
