package com.zzw.common.model.req.account;

import lombok.Data;

@Data
public class forgetPswReq {
    String Email;
    String code;
}
