package com.zzw.common.model.resp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizeResp {
    Integer id;
    String token;
    String role;
    String name;
    String CreateTime;
}
