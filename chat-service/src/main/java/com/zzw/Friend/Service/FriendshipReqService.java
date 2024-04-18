package com.zzw.Friend.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.Friend.Entiy.FriendshipReq;
import com.zzw.common.model.req.friend.AddFriendReq;

public interface FriendshipReqService extends IService<FriendshipReq> {

    String SelAllReq(Integer FromId);
    String AddReq(Integer FromId, AddFriendReq req);

    Boolean selOneReq(Integer FromId,Integer ToId);

    void UpdateReaded(Integer FromId,Integer ToId);
}
