package com.zzw.Account.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzw.Account.Entiy.Account;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}
