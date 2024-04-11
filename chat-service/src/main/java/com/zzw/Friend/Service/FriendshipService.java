package com.zzw.Friend.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.Friend.Entiy.Friendship;


public interface FriendshipService extends IService<Friendship> {

    String SelShip(Integer FromId,Integer ToId);

    String CheckStatus(Integer FromId,Integer ToId);
}
