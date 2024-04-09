package com.zzw.common.model.req.account;


import lombok.Data;

import java.util.Date;

@Data
public class EnRollReq {

    String accountUsername;

    String accountPassword;

    String accountRole = "user";

    String registerTime =  new Date().toString();

    String accountEmail;

    String code;


}
