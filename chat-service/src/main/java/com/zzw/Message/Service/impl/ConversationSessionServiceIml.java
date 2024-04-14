package com.zzw.Message.Service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzw.Message.Dao.ConversationSessionMapper;
import com.zzw.Message.Entiy.ConversationSession;
import com.zzw.Message.Service.ConversationSessionService;
import com.zzw.common.model.RestBean;
import org.springframework.stereotype.Service;


@Service
public class ConversationSessionServiceIml extends ServiceImpl<ConversationSessionMapper, ConversationSession> implements ConversationSessionService {

    @Override
    public String GetAllSession(Integer from_id) {
        Wrapper<ConversationSession> wrapper = new QueryWrapper<ConversationSession>().eq("from_id",from_id);
        return RestBean.success(this.list(wrapper)).ToJSON();
    }
}
