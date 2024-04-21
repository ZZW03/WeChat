package com.zzw.Friend.Entiy;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "db_friendship")
public class Friendship {
    Integer     fromId;
    Integer     toId;
    Integer     status;
    String      createTime = new Date().toString();
}
