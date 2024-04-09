package com.zzw.Config;


import com.zzw.Account.Entiy.Account;
import com.zzw.Account.Service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeService implements UserDetailsService {

    @Resource
    AccountService accountService;

    @Resource
    private PasswordEncoder encoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        //通过username 查询是否存在用户
        Account account = accountService.FindByName(username);


        if(account == null ){
            throw new UsernameNotFoundException("用户名没有注册");
        }

        //返回信息后 进行密码对比
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                //密码一定要加密才行
                .password(encoder.encode(account.getAccountPassword()))
                .roles(account.getAccountRole())
                .build();
    }
}
