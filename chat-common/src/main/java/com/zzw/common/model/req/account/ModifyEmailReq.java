package com.zzw.common.model.req.account;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ModifyEmailReq {

    @NotBlank
    String email;
    @NotBlank
    String code;
    @NotBlank
    String newEmail;
}
