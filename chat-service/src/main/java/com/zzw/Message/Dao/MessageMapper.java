package com.zzw.Message.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzw.Message.Entiy.DBMessage;
import com.zzw.common.model.resp.SessionDetailResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MessageMapper extends BaseMapper<DBMessage> {


    // 查某个session中的第一条信息
    @Select("SELECT from_id ,account_nick_name , message_body " +
            "from db_message a left join db_message_history b on a.message_id =b.message_id " +
            "left join db_conversation_session_body c on b.message_id = c.message_id " +
            "left join db_account_details d on b.from_id = d.account_id"+
            " where c.session_id = #{SessionId} ORDER BY a.message_id DESC limit 1")
    SessionDetailResp SelOneMessageInSession(Integer SessionId);

}
