package com.zzw.Account.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzw.Account.Dao.AccountPrivacyDao;
import com.zzw.Account.Entiy.AccountPrivacy;
import com.zzw.Account.Service.AccountPrivacyService;
import com.zzw.common.model.RestBean;
import com.zzw.common.model.enums.UserError;
import com.zzw.common.model.req.account.UpdatePrivacyReq;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountPrivacyServiceImpl extends ServiceImpl<AccountPrivacyDao, AccountPrivacy> implements AccountPrivacyService {
    @Override
    public String updatePrivacy(UpdatePrivacyReq req, Integer id) {
        AccountPrivacy accountPrivacy1 = this.getById(id);
        Wrapper<AccountPrivacy> wrapper = new QueryWrapper<AccountPrivacy>().eq("account_id",id);
        AccountPrivacy accountPrivacy = new AccountPrivacy();
        BeanUtils.copyProperties(accountPrivacy1,accountPrivacy);
        switch (req.getTypes()){
            case "phone": accountPrivacy.setPhone(req.getStatus()) ;break;
            case "qq": accountPrivacy.setQq(req.getStatus());break;
            case "accountSex": accountPrivacy.setAccountSex(req.getStatus());break;
            case "accountEmail": accountPrivacy.setAccountEmail(req.getStatus());break;

        }

        if (this.update(accountPrivacy,wrapper)) {
            return RestBean.success("修改成功").ToJSON();
        } else {
            return RestBean.error(UserError.USER_SYSTEM_ERROR.getCode(), UserError.USER_SYSTEM_ERROR.getError()).ToJSON();
        }
    }
}
