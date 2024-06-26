package com.zzw.Friend.Controller;

import com.zzw.Account.Service.AccountService;
import com.zzw.Friend.Entiy.Friendship;
import com.zzw.Friend.Service.FriendshipReqService;
import com.zzw.Friend.Service.FriendshipService;
import com.zzw.common.Const;
import com.zzw.common.model.req.friend.AddFriendReq;
import com.zzw.common.model.req.friend.CheckStatusReq;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("friend")
public class FriendController {


    @Resource
    FriendshipService shipService;

    @Resource
    FriendshipReqService reqService;

    @PostMapping("AddFriend")
    public String AddFriend(@RequestBody AddFriendReq req,
                            @RequestAttribute(Const.ACCOUNT.ATTR_USER_ID) Integer FromId){
        System.out.println(req);
        return reqService.AddReq(FromId,req);
    }

    @GetMapping("SelShip")
    public String SelShip(@RequestAttribute(Const.ACCOUNT.ATTR_USER_ID) Integer FromId){
        return  shipService.GetAllShip(FromId);
    }

    @GetMapping("SelAllReq")
    public String SelAllReq(@RequestAttribute(Const.ACCOUNT.ATTR_USER_ID)Integer FromId){
        return reqService.SelAllReq(FromId);
    }

    @PostMapping("CheckStatus")
    public String CheckFriend(@RequestBody CheckStatusReq req){
        return shipService.CheckStatus(req);
    }
}
