package com.zzw.Account.Entiy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "db_account_privacy")
public class AccountPrivacy {
    @TableId(value = "account_id",type = IdType.ASSIGN_ID)
    Integer accountId;
    Boolean phone = false;
    Boolean qq = false;
    Boolean accountSex = false;
    Boolean accountEmail = false;
}
