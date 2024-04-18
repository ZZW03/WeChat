package com.zzw.Account.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzw.Account.Entiy.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    @Select("select account_id from db_account where account_username = #{username}")
    public Integer getIdByName(String username);

}
