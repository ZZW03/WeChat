package com.zzw.Friend.Service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzw.Account.Entiy.Account;
import com.zzw.Friend.Dao.FriendshipMapper;
import com.zzw.Friend.Entiy.Friendship;
import com.zzw.Friend.Service.FriendshipService;
import com.zzw.common.model.RestBean;
import com.zzw.common.model.enums.FriendShipError;
import com.zzw.common.model.req.friend.CheckStatusReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipServiceImpl extends ServiceImpl<FriendshipMapper, Friendship> implements FriendshipService {


    @Override
    public String SelShip(Integer FromId, Integer ToId) {
        return RestBean.success(this.query()
                .eq("from_id",FromId)
                .eq("to_id",ToId)
                .one()
        ).ToJSON();

    }


    @Override
    public String CheckStatus(CheckStatusReq req) {
        System.out.println(req);
        Friendship FromShip = this.query().eq("from_id", req.getFromId()).eq("to_id", req.getToId()).one();
        Friendship ToShip   = this.query().eq("from_id", req.getToId()).eq("to_id", req.getFromId()).one();
        if(!FromShip.getStatus().equals(0)){
            return RestBean.error(FriendShipError.HE_IN_YOUR_BLACK.getCode(),FriendShipError.HE_IN_YOUR_BLACK.getError()).ToJSON();
        }

        if(!ToShip.getStatus().equals(0)){
            return RestBean.error(FriendShipError.U_IN_HIS_BLACK.getCode(),FriendShipError.U_IN_HIS_BLACK.getError()).ToJSON();
        }

        return RestBean.success().ToJSON();
    }

    @Override
    public String GetAllShip(Integer FromId) {
        Wrapper<Friendship> wrapper = new QueryWrapper<Friendship>().eq("from_id", FromId);
        return RestBean.success(this.list(wrapper)).ToJSON();
    }
}
