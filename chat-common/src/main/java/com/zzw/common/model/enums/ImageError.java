package com.zzw.common.model.enums;

import lombok.Getter;

@Getter
public enum ImageError {

    UPLOAD_IMAGE_ERROR(20000,"上传失败"),
    UPLOAD_TOO_MANY(20001,"上传太频发，请稍后重试"),
    IMAGE_TOO_BIG(20002,"图片不能大于5M"),

    ;
    private final int code;

    private final String error;

    ImageError(int code, String error) {
        this.code = code;
        this.error = error;
    }
}
