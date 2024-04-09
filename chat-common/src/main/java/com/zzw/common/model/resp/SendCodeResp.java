package com.zzw.common.model.resp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendCodeResp {
    Integer code;
    String message;
}
