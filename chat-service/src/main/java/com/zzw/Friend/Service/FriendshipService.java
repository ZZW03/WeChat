package com.zzw.Friend.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.Friend.Entiy.Friendship;
import com.zzw.common.model.req.friend.CheckStatusReq;


public interface FriendshipService extends IService<Friendship> {

    String SelShip(Integer FromId,Integer ToId);

    Friendship IsSelShip(Integer FromId, Integer ToId);

    String CheckStatus(CheckStatusReq req);

    String GetAllShip(Integer FromId);

    String AddFriend(Integer fromId, Integer toId ,Integer reqId);
}
