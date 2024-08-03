package com.zzw.Message.Service;

import com.zzw.Message.Entiy.DBMessage;
import com.zzw.Message.Entiy.HistoryMessage;

public interface StoreMessageService {

    void storeMessage(DBMessage dbMessage);

    void storeMessageHistory(HistoryMessage historyMessage);
}
