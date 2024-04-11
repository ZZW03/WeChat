package com.zzw.Friend.Controller;

import com.zzw.Account.Service.AccountService;
import com.zzw.Friend.Service.FriendshipReqService;
import com.zzw.Friend.Service.FriendshipService;
import com.zzw.common.Const;
import com.zzw.common.model.req.friend.AddFriendReq;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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
        return reqService.AddReq(FromId,req);
    }
}
