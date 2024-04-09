package com.zzw.common.model.req.account;

import lombok.Data;

@Data
public class AccountDetailsReq {
    String  accountNickName;
    String  accountAddress;
    Integer accountSex;
    String  accountAge;
    String  accountQq;
    String  accountPhone;
}
