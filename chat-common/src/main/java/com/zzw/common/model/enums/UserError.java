package com.zzw.common.model.enums;

import lombok.Getter;

@Getter
public enum UserError {

    USER_MODIFY_EMAIL_ERROR(10000,"原邮箱错误"),
    USER_MODIFY_CODE_ERROR(10001,"请检查邮箱或者验证码是否正确"),
    USER_RESET_PASSWORD_ERROR(10002,"密码错误"),
    USER_PASSWORD_NOT_REAPEAT(10002,"密码错误"),
    USER_ENROLL_ERROR(10003,"用户名重复了")
    ;


    private final int code;

    private final String error;

    UserError(int code, String error){
        this.code = code;
        this.error = error;
    }

}
