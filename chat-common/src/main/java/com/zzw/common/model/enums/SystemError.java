package com.zzw.common.model.enums;

import lombok.Getter;

@Getter
public enum SystemError {

        NOT_FIND(404,"没有找到"),
        USER_SYSTEM_ERROR(401,"系统错误，稍后重试"),

    ;

    private final int code;

    private final String error;

    SystemError(int code, String error) {
        this.code = code;
        this.error = error;
    }
}
