package com.zzw.Account.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.Account.Entiy.Account;
import com.zzw.common.model.req.account.*;

import java.util.List;

public interface AccountService extends IService<Account> {

    public String getAccount(Integer id);

    public boolean saveBatchCustom(List<Account> list);

    Boolean SaveAccount(AccountReq accountReq);

    Account FindByNameOrByEmail(String text);

    Account FindByName(String name);

    String SendCode(SendCodeReq req, String ip);

    String resetPsw(Integer id , ResetPswReq req);

    String modifyEmail(Integer id, ModifyEmailReq modifyEmailReq) ;

    String enRoll(EnRollReq req);

    String confirmCode(ModifyEmailReq req);

    String forgetPsw(ResetPswReq req);
}
