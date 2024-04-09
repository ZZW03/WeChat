package com.zzw.common.proto;

import lombok.Data;

import java.io.Serializable;


@Data
public class MessagePack<T> implements Serializable {

    private String userId;

    private String toId;

    private String messageId;

    private Integer command;

    private T data;


}
