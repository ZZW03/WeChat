package com.zzw.common.proto;

import lombok.Data;


@Data
public class MessageHeader {

    private Integer command;

    //4字节 解析类型
    private Integer messageType = 0x0;

    //4字节 包体长度
    private int length;

}
