package com.zzw.common.model.enums;

import lombok.Getter;

@Getter
public enum FriendShipError {
        HE_IN_YOUR_BLACK(11000,"他已经被你拉黑"),
        U_IN_HIS_BLACK(11001,"你已经被他拉黑"),
        NOT_HAVE_FRIEND(11002,"找不到此用户"),
        CANT_ADD_YOURSELF(11003,"不能添加自己"),
        NOT_FIND(11004,"查无此人")
    ;
    private final Integer code;
    private final String error;

    FriendShipError(Integer code, String error) {
        this.code = code;
        this.error = error;
    }
}
