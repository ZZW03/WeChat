package com.zzw.Account.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzw.Account.Dao.AccountMapper;
import com.zzw.Account.Entiy.Account;
import com.zzw.Account.Entiy.AccountDetails;
import com.zzw.Account.Service.AccountDetailService;
import com.zzw.Account.Service.AccountService;
import com.zzw.Utils.FlowUtil;
import com.zzw.common.Const;
import com.zzw.common.model.RestBean;
import com.zzw.common.model.enums.SystemError;
import com.zzw.common.model.enums.UserError;
import com.zzw.common.model.req.account.*;
import com.zzw.common.model.resp.SendCodeResp;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AccountServiceImpl  extends ServiceImpl<AccountMapper, Account> implements AccountService {


    @Resource
    FlowUtil flowUtil;

    @Resource
    AmqpTemplate amqpTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    AccountDetailService accountDetailService;


    @Override
    public Account FindByName(String name) {
        return this.query()
                .eq("account_username", name)
                .one();
    }


    @Override
    public String getAccount(Integer id) {
        Account account = this.getById(id);
        AccountReq req = new AccountReq();
        BeanUtils.copyProperties(account,req);
        req.setAccountPassword("");
        req.setId(id);
        return RestBean.success(req).ToJSON();
    }


    @Transactional
    @Override
    public boolean saveBatchCustom(List<Account> list){
        return this.saveBatch(list);
    }

    @Override
    public Boolean SaveAccount(AccountReq accountReq){
        Account account = new Account();
        BeanUtils.copyProperties(accountReq,account);
        return this.save(account);
    }

    @Override
    public Account FindByNameOrByEmail(String text) {
        return this.query()
                .eq("account_username", text).or()
                .eq("account_email",text)
                .one();
    }


    @Override
    public String SendCode(SendCodeReq req, String ip) {
        synchronized (ip.intern()){
            if(flowUtil.LimitOnceCheck(Const.REDIS.LIMIT_IP_BLACK + ip,1)){
                Random random = new Random();
                int code = random.nextInt(899999) + 100000;
                Map<String, Object> map = Map.of("type", req.getType(), "email" , req.getEmail() , "code" , code);
                amqpTemplate.convertAndSend(Const.MQ.MQ_QUEUE_NAME_MAIL,map);
                stringRedisTemplate.opsForValue()
                        .set(Const.REDIS.VERIFY_EMAIL_CODE + req.getEmail(),String.valueOf(code),3, TimeUnit.MINUTES);
                return RestBean.success(new SendCodeResp(200,"发送成功")).ToJSON();
            }
            return RestBean.success(new SendCodeResp(401,"发送太频繁，稍后重试")).ToJSON();
        }
    }

    @Override
    public String resetPsw(Integer id, ResetPswReq req) {
        Account account = this.getById(id);
        if(req.getNewPasswordRepeat().equals(req.getNewPassword()) && req.getPassword().equals(account.getAccountPassword())){
            Wrapper<Account> wrapper = new QueryWrapper <Account>()
                    .eq("account_id", id);
            account.setAccountPassword(req.getNewPassword());
            if(this.update(account,wrapper)){
                return RestBean.success("修改成功").ToJSON();
            }else {
                return RestBean.error(SystemError.USER_SYSTEM_ERROR.getCode(), SystemError.USER_SYSTEM_ERROR.getError()).ToJSON();
            }
        }else{
            return RestBean.error(UserError.USER_RESET_PASSWORD_ERROR.getCode(), UserError.USER_RESET_PASSWORD_ERROR.getError()).ToJSON();
        }


    }

    @Override
    public String modifyEmail(Integer id , ModifyEmailReq req) {
        Account account = this.getById(id);
        String code = stringRedisTemplate.opsForValue().get(Const.REDIS.VERIFY_EMAIL_CODE + req.getNewEmail());
        String email = account.getAccountEmail();

        if(!email.equals(req.getEmail())){
            return RestBean.error(UserError.USER_MODIFY_EMAIL_ERROR.getCode(), UserError.USER_MODIFY_EMAIL_ERROR.getError()).ToJSON();
        }
        if (code != null && code.equals(req.getCode())) {
            Wrapper<Account> wrapper = new QueryWrapper<Account>().eq("account_id", id);
            account.setAccountEmail(req.getNewEmail());
            if (this.update(account,wrapper)) {
                return RestBean.success("修改成功").ToJSON();
            } else {
                return RestBean.error(403,"修改失败,稍后重试").ToJSON();
            }
        }else{
            return RestBean.error(UserError.USER_MODIFY_CODE_ERROR.getCode(), UserError.USER_MODIFY_CODE_ERROR.getError()).ToJSON();
        }
    }

    @Override
    public String enRoll( EnRollReq req) {

        String code = stringRedisTemplate.opsForValue().get(Const.REDIS.VERIFY_EMAIL_CODE + req.getAccountEmail());
        if(this.FindByName(req.getAccountUsername()) != null)
            return RestBean.error(UserError.USER_ENROLL_ERROR.getCode(), UserError.USER_ENROLL_ERROR.getError()).ToJSON();

        Account account = new Account();
        AccountDetails accountDetails = new AccountDetails();
        BeanUtils.copyProperties(req,accountDetails);
        BeanUtils.copyProperties(req,account);
        account.setAccountPassword((req.getAccountPassword()));
        if (code != null && code.equals(req.getCode())) {
            if(this.save(account)){
                Integer accountId = this.FindByName(req.getAccountUsername()).getAccountId();
                accountDetails.setAccountId(accountId);
                accountDetailService.save(accountDetails);
                return RestBean.success("注册成功").ToJSON();
            }else{
                return RestBean.error(SystemError.USER_SYSTEM_ERROR.getCode(), SystemError.USER_SYSTEM_ERROR.getError()).ToJSON();
            }
        }else{
            return RestBean.error(UserError.USER_MODIFY_CODE_ERROR.getCode(), UserError.USER_MODIFY_CODE_ERROR.getError()).ToJSON();
        }
    }

    @Override
    public String confirmCode(ModifyEmailReq req) {
        String code = stringRedisTemplate.opsForValue().get(Const.REDIS.VERIFY_EMAIL_CODE + req.getEmail());
        if (code != null && code.equals(req.getCode()))
            return RestBean.success("请写入新密码").ToJSON();
        return RestBean.error(UserError.USER_MODIFY_CODE_ERROR.getCode(), UserError.USER_MODIFY_CODE_ERROR.getError()).ToJSON();
    }

    @Override
    public String forgetPsw(ResetPswReq req) {

        Wrapper<Account> wrapper = new QueryWrapper<Account>().eq("account_email",req.getEmail());
        Account account = this.FindByNameOrByEmail(req.getEmail());
        if (req.getNewPassword().equals(req.getNewPasswordRepeat())){
            account.setAccountPassword(req.getNewPassword());

            if (this.update(account,wrapper)) {
                return RestBean.success("密码修改完成").ToJSON();
            }else{
                return RestBean.error(SystemError.USER_SYSTEM_ERROR.getCode(), SystemError.USER_SYSTEM_ERROR.getError()).ToJSON();
            }

        }
        return RestBean.error(UserError.USER_PASSWORD_NOT_REAPEAT.getCode(), UserError.USER_PASSWORD_NOT_REAPEAT.getError()).ToJSON();
    }


}
