package com.zzw.Account.Entiy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "db_account_details")
public class AccountDetails {
    @TableId(value = "account_id",type = IdType.ASSIGN_ID)//主键自增
    Integer accountId;
    String  accountAvatar = "";
    String  accountNickName = "未命名";
    String  accountAddress = "中国";
    Integer accountSex = 1;
    String  accountAge = "18";
    String  accountQq = "";
    String  accountPhone = "";
}
