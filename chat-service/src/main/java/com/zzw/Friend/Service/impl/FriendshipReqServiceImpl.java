package com.zzw.Friend.Service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzw.Account.Entiy.Account;
import com.zzw.Account.Service.AccountService;
import com.zzw.Friend.Dao.FriendshipReqMapper;
import com.zzw.Friend.Entiy.FriendshipReq;
import com.zzw.Friend.Service.FriendshipReqService;
import com.zzw.common.model.RestBean;
import com.zzw.common.model.enums.FriendShipError;
import com.zzw.common.model.enums.SystemError;
import com.zzw.common.model.req.friend.AddFriendReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class FriendshipReqServiceImpl extends ServiceImpl<FriendshipReqMapper, FriendshipReq> implements FriendshipReqService {

    @Resource
    AccountService accountService;

    @Override
    public String SelAllReq(Integer FromId) {
        Wrapper<FriendshipReq> wrapper = new QueryWrapper<FriendshipReq>().eq("to_id",FromId);
        return RestBean.success(this.list(wrapper)).ToJSON();
    }

    @Override
    public String AddReq(Integer FromId, AddFriendReq req) {

        Account account = accountService.getById(req.getToId());

        if(account == null){
            return RestBean.error(FriendShipError.NOT_HAVE_FRIEND.getCode(),FriendShipError.NOT_HAVE_FRIEND.getError()).ToJSON();
        }

        if(account.getAccountId().equals(FromId)){
            return RestBean.error(FriendShipError.CANT_ADD_YOURSELF.getCode(),FriendShipError.CANT_ADD_YOURSELF.getError()).ToJSON();
        }


        FriendshipReq friendshipReq = new FriendshipReq();
        friendshipReq.setFromId(FromId);
        friendshipReq.setToId(account.getAccountId());
        friendshipReq.setReadStatus(0);
        friendshipReq.setApproveStatus(0);
        friendshipReq.setAddWording(req.getWording());
        if (this.save(friendshipReq)) {
            RestBean.success("添加成功").ToJSON();
        }else {
            RestBean.error(SystemError.USER_SYSTEM_ERROR.getCode(),SystemError.USER_SYSTEM_ERROR.getError()).ToJSON();
        }
        return RestBean.success().ToJSON();
    }

    @Override
    public Boolean selOneReq(Integer FromId, Integer ToId) {
        FriendshipReq one = this.query()
                .eq("from_id", FromId)
                .eq("to_id", ToId)
                .one();
        return one == null;
    }

    @Override
    public void UpdateReaded(Integer FromId, Integer ToId) {
        System.out.println("进入");
//        Wrapper<FriendshipReqServiceImpl> wrapper = new QueryWrapper<FriendshipReqServiceImpl>()
//                .eq("from_id",FromId)
//                .eq("to_id",ToId);
        this.update()
                .eq("from_id", FromId)
                .eq("to_id", ToId)
                .set("read_status",0)
                .update();
    }
}
