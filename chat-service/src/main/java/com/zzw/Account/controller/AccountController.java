package com.zzw.Account.controller;


import com.zzw.Account.Service.AccountDetailService;
import com.zzw.Account.Service.AccountPrivacyService;
import com.zzw.Account.Service.AccountService;
import com.zzw.common.Const;
import com.zzw.common.model.RestBean;
import com.zzw.common.model.req.account.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Resource
    AccountPrivacyService accountPrivacyService;

    @Resource
    AccountDetailService accountDetailService;

    @Resource
    AccountService accountService;

    @PostMapping("sendcode")
    public String SendCode(@RequestBody SendCodeReq req, HttpServletRequest request){
        return accountService.SendCode(req,request.getRemoteAddr());
    }

    @PostMapping("modifyemail")
    public String ModifyEmail(@RequestAttribute(Const.ACCOUNT.ATTR_USER_ID) Integer id,
                              @RequestBody ModifyEmailReq req){
        return accountService.modifyEmail(id,req);
    }

    @PostMapping("resetPsw")
    public String ResetPsw(@RequestAttribute(Const.ACCOUNT.ATTR_USER_ID) Integer id,
                           @RequestBody ResetPswReq req){
        return accountService.resetPsw(id,req);
    }
    @PostMapping("enRoll")
    public String EnRoll(@RequestBody EnRollReq req){

        return accountService.enRoll(req);
    }

    @GetMapping("confirmCode")
    public String ConfirmCode(ModifyEmailReq req){
        return accountService.confirmCode(req);
    }

    @PostMapping("forgetPsw")
    public String forgetPsw(@RequestBody  ResetPswReq req){
        return accountService.forgetPsw(req);
    }

    @GetMapping("getDetail")
    public String getDetail(@RequestAttribute(Const.ACCOUNT.ATTR_USER_ID) Integer id){
        return RestBean.success(accountDetailService.GetDetail(id)).ToJSON();
    }

    @GetMapping("getOtherDetail")
    public String getOtherDetail(Integer id){
        return RestBean.success(accountDetailService.GetDetail(id)).ToJSON();
    }


    @GetMapping("getAccount")
    public String getAccount(@RequestAttribute(Const.ACCOUNT.ATTR_USER_ID) Integer id){
        return accountService.getAccount(id);
    }

    @PostMapping("updateDetail")
    public String updateDetail(@RequestBody AccountDetailsReq req,
                               @RequestAttribute(Const.ACCOUNT.ATTR_USER_ID) Integer id){
        return accountDetailService.updateDetail(req,id);
    }

    @PostMapping("updatePrivacy")
    public String updatePrivacy(@RequestBody UpdatePrivacyReq req,
                                @RequestAttribute(Const.ACCOUNT.ATTR_USER_ID) Integer id){

        return accountPrivacyService.updatePrivacy(req,id);

    }
    @GetMapping("getPrivacy")
    public String getPrivacy(@RequestAttribute(Const.ACCOUNT.ATTR_USER_ID) Integer id){
            return RestBean.success(accountPrivacyService.getById(id)).ToJSON();
    }




}
