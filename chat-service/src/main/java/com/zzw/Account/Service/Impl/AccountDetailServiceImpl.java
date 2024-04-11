package com.zzw.Account.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzw.Account.Dao.AccountDetailMapper;
import com.zzw.Account.Entiy.AccountDetails;
import com.zzw.Account.Service.AccountDetailService;
import com.zzw.common.model.RestBean;
import com.zzw.common.model.enums.SystemError;
import com.zzw.common.model.enums.UserError;
import com.zzw.common.model.req.account.AccountDetailsReq;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailServiceImpl extends ServiceImpl<AccountDetailMapper, AccountDetails> implements AccountDetailService {

    @Override
    public AccountDetails GetDetail(Integer id) {
        return this.query()
                .eq("account_id",id)
                .one();
    }

    @Override
    public String updateDetail(AccountDetailsReq req, Integer id) {
        AccountDetails accountDetails = new AccountDetails();
        BeanUtils.copyProperties(req,accountDetails);
        Wrapper<AccountDetails> wrapper = new QueryWrapper<AccountDetails>().eq("account_id",id);
        if (this.update(accountDetails,wrapper)) {
            return RestBean.success("修改成功").ToJSON();
        }else {
            RestBean.error(SystemError.USER_SYSTEM_ERROR.getCode(), SystemError.USER_SYSTEM_ERROR.getError()).ToJSON();
        }
        return RestBean.success().ToJSON();
    }

}
