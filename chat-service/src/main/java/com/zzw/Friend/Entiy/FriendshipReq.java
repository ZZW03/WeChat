package com.zzw.Friend.Entiy;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "db_friendship_req")
public class FriendshipReq {
    Integer fromId;
    Integer toId;
    Integer readStatus;
    String addWording;
    Integer approveStatus;
    String  createTime;

}
