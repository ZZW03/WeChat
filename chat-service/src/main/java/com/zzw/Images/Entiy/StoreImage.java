package com.zzw.Images.Entiy;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@TableName("db_image_store")
@AllArgsConstructor
public class StoreImage {
    @TableId(value = "account_id")//主键自增
    int accountId;
    @TableField(value="avatar_name")
    String avatarName;
    @TableField(value="avatar_create_time")
    Date avatarCreateTime;
}
