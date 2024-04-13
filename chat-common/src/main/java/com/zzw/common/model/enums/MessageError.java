package com.zzw.common.model.enums;


import lombok.Getter;
import lombok.Setter;

@Getter
public enum MessageError {
        MESSAGE_SEND_ERROR(30000,"信息发送失败"),
        NOT_FRIEND(30001,"你们不是朋友"),
        U_IN_HIS_BLACKLIST(30002,"你已经被他拉黑了");

    private final int code;

    private final String error;

    MessageError(int code, String error) {
        this.code = code;
        this.error = error;
    }
}
