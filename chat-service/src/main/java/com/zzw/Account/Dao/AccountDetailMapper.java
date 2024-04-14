package com.zzw.Account.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzw.Account.Entiy.AccountDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountDetailMapper extends BaseMapper<AccountDetails> {

    @Select("select account_nick_name from db_account_details where account_id = #{id}")
    public String SelName(Integer id);

}


