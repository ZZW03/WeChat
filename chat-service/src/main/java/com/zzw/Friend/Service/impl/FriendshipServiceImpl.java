package com.zzw.Friend.Service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzw.Friend.Dao.FriendshipMapper;
import com.zzw.Friend.Entiy.Friendship;
import com.zzw.Friend.Service.FriendshipReqService;
import com.zzw.Friend.Service.FriendshipService;
import com.zzw.common.model.RestBean;
import com.zzw.common.model.enums.FriendShipError;
import com.zzw.common.model.req.friend.CheckStatusReq;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FriendshipServiceImpl extends ServiceImpl<FriendshipMapper, Friendship> implements FriendshipService {

    @Lazy
    @Resource
    FriendshipReqService friendshipReqService;

    @Override
    public String SelShip(Integer FromId, Integer ToId) {
        return RestBean.success(this.query()
                .eq("from_id",FromId)
                .eq("to_id",ToId)
                .one()
        ).ToJSON();
    }

    @Override
    public Friendship IsSelShip(Integer FromId, Integer ToId) {
        return this.query()
                .eq("from_id",FromId)
                .eq("to_id",ToId)
                .one();
    }


    @Override
    public String CheckStatus(CheckStatusReq req) {

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


    @Override
    public String AddFriend(Integer fromId, Integer toId,Integer reqID) {

        if(this.IsSelShip(fromId,toId) !=null || this.IsSelShip(toId,fromId)!= null )
            return RestBean.error(FriendShipError.U_ARE_FRIEND_ALREADY.getCode(),FriendShipError.U_ARE_FRIEND_ALREADY.getError()).ToJSON();

        Friendship fShip = new Friendship();
        fShip.setStatus(0);
        fShip.setToId(toId);
        fShip.setFromId(fromId);

        Friendship tShip = new Friendship();
        tShip.setStatus(0);
        tShip.setToId(fromId);
        tShip.setFromId(toId);

        List<Friendship> friendshipList = new ArrayList<>();
        friendshipList.add(fShip);
        friendshipList.add(tShip);


        if(this.saveBatch(friendshipList)){
            boolean update = friendshipReqService.update()
                    .eq("req_id", reqID)
                    .set("approve_status", 1)
                    .update();
            if(update){
                return RestBean.success("添加成功").ToJSON();
            }else{
                return RestBean.error(FriendShipError.ADD_FAIL.getCode(), FriendShipError.ADD_FAIL.getError()).ToJSON();
            }

        }else{
            return RestBean.error(FriendShipError.ADD_FAIL.getCode(), FriendShipError.ADD_FAIL.getError()).ToJSON();
        }


    }
}
