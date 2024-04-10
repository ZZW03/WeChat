package com.zzw.common.model;

import lombok.Data;


@Data
public class UserSession {

    private Integer userId;

    //连接状态 1=在线 2=离线
    private Integer connectState;

}
