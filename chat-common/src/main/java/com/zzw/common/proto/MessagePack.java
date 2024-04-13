package com.zzw.common.proto;

import lombok.Data;

import java.io.Serializable;


@Data
public class MessagePack<T> implements Serializable {

    private Integer userId;

    private Integer toId;

    private Integer messageId;

    private Integer command;

    private T data;

}
