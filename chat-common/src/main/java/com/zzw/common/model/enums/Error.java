package com.zzw.common.model.enums;

import lombok.Getter;

@Getter
public enum Error {

        NOT_FIND(404,"没有找到"),

    ;

    private final int code;

    private final String error;

    Error(int code, String error) {
        this.code = code;
        this.error = error;
    }
}
