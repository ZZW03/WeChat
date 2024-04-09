package com.zzw.common.model.req.account;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPswReq {
    @NotBlank
    String email;
    @NotBlank
    String password;
    @NotBlank
    String newPassword;
    @NotBlank
    String newPasswordRepeat;
}
