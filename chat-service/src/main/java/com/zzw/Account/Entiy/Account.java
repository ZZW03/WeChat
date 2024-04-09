package com.zzw.Account.Entiy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "db_account")
public class Account {
    @TableId(value = "account_id",type = IdType.AUTO)//主键自增
    Integer accountId;
    @TableField(value = "account_username")
    String accountUsername;
    @TableField(value = "account_password")
    String accountPassword;
    @TableField(value = "account_role")
    String accountRole;
    @TableField(value = "register_time")
    String registerTime;
    @TableField(value = "account_email")
    String accountEmail;
}
