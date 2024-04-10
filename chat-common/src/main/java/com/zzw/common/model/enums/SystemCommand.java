package com.zzw.common.model.enums;

import lombok.Getter;

@Getter
public enum SystemCommand {
    LOGIN(0x2328);

    private final int command;

    SystemCommand(int command) {
        this.command = command;
    }
}
