package com.zzw.common.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MessageContent {

    private Integer messageId;

    private Integer fromId;

    private Integer toId;

    private String nickName;

    private Integer command;

    private Object messageBody;

    private Long messageTime = new Date().getTime();

}
