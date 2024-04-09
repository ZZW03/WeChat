package com.zzw.Account.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.Account.Entiy.AccountDetails;
import com.zzw.common.Const;
import com.zzw.common.model.req.account.AccountDetailsReq;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;

public interface AccountDetailService extends IService<AccountDetails> {
    AccountDetails GetDetail(Integer id);

    String updateDetail( AccountDetailsReq req, Integer id);
}
