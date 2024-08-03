package com.zzw.Message.Service.impl;

import com.zzw.Message.Dao.HistoryMessageMapper;
import com.zzw.Message.Dao.MessageMapper;
import com.zzw.Message.Entiy.DBMessage;
import com.zzw.Message.Entiy.HistoryMessage;
import com.zzw.Message.Service.StoreMessageService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class StoreMessageServiceImpl implements StoreMessageService {

    @Resource
    MessageMapper messageMapper;

    @Resource
    HistoryMessageMapper historyMessageMapper;


    @Override
    public void storeMessage(DBMessage dbMessage) {
        messageMapper.insert(dbMessage);
    }

    @Override
    public void storeMessageHistory(HistoryMessage historyMessage) {
        historyMessageMapper.insert(historyMessage);
    }
}
