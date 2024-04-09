package com.zzw.Account.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.Account.Entiy.AccountPrivacy;
import com.zzw.common.model.req.account.UpdatePrivacyReq;

public interface AccountPrivacyService extends IService<AccountPrivacy> {

    String updatePrivacy(UpdatePrivacyReq req,Integer id);
}
