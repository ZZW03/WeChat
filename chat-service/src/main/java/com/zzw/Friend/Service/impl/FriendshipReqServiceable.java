package com.zzw.Friend.Service.impl;

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

import javax.crypto.spec.PSource;

@Service
public class FriendshipReqServiceable extends ServiceImpl<FriendshipReqMapper, FriendshipReq> implements FriendshipReqService {

    @Resource
    AccountService accountService;

    @Override
    public String AddReq(Integer FromId, AddFriendReq req) {

        Account account = accountService.FindByName(req.getToUsername());

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
}
