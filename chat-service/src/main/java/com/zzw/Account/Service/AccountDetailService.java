package com.zzw.Account.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.Account.Entiy.AccountDetails;
import com.zzw.common.model.req.account.AccountDetailsReq;


public interface AccountDetailService extends IService<AccountDetails> {
    AccountDetails GetDetail(Integer id);

    String updateDetail( AccountDetailsReq req, Integer id);
}
