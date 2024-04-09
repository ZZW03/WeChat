package com.zzw.common.model.req.account;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SendCodeReq {
    @NotNull
    String email;
    @NotNull
    String type;

}
