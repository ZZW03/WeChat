package com.zzw.common.model.enums;

import lombok.Getter;

@Getter
public enum SystemCommand {
    //登录
    LOGIN(0x2328),

    //离线通知
    FORCEOFFLINE(0x232a);

    private final int command;

    SystemCommand(int command) {
        this.command = command;
    }
}
