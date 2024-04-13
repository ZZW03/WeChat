package com.zzw.common.model.dto;

import lombok.Data;

@Data
public class MessageContent {

    private Integer messageId;

    private Integer fromId;

    private Integer toId;

    private Integer command;

    private Object messageBody;

    private Long messageTime;

}
