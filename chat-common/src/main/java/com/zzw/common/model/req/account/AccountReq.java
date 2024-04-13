package com.zzw.common.model.req.account;

import lombok.Data;

@Data
public class AccountReq {
    Integer id;

    String accountUsername;

    String accountPassword;

    String accountRole;

    String registerTime;

    String accountEmail;
}
