package com.zzw.common.model.req.friend;

import lombok.Data;

@Data
public class AddFriendReq {
    Integer toId;
    String toUsername;
    String wording;
}
