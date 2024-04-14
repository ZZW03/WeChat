package com.zzw.Message.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.Message.Entiy.ConversationSession;
import org.springframework.stereotype.Service;

public interface ConversationSessionService extends IService<ConversationSession> {

    String GetAllSession(Integer from_id);
}
