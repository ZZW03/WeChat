package com.zzw.Account.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzw.Account.Entiy.AccountDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountDetailMapper extends BaseMapper<AccountDetails> {
}
