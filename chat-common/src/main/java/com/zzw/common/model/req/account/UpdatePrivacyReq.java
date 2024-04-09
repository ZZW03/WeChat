package com.zzw.common.model.req.account;

import lombok.Data;

@Data
public class UpdatePrivacyReq {
    String types;
    Boolean status;
}
