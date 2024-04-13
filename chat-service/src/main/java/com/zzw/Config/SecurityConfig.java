package com.zzw.Config;


import com.zzw.Account.Entiy.Account;
import com.zzw.Account.Service.AccountService;


import com.zzw.Filter.JwtAuthorizedFilter;
import com.zzw.Utils.JwtUtil;
import com.zzw.common.model.RestBean;
import com.zzw.common.model.resp.AuthorizeResp;
import com.zzw.common.model.resp.LogoutResp;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;
import java.io.PrintWriter;


@Configuration
public class SecurityConfig {

    @Resource
    JwtUtil util;

    @Resource
    JwtAuthorizedFilter jwtAuthorizedFilter;

    @Resource
    AccountService accountService;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(config -> config
                        .requestMatchers("/auth/*").permitAll()
                        .requestMatchers("/account/sendcode").permitAll()
                        .requestMatchers("/account/enRoll").permitAll()
                        .requestMatchers("/account/confirmCode").permitAll()
                        .requestMatchers("/account/forgetPsw").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/cache/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(conf -> {
                    conf.loginProcessingUrl("/auth/login");
                    //使用自定义的成功失败处理器
                    conf.failureHandler(this::onAuthenticationFailure);
                    conf.successHandler(this::onAuthenticationSuccess);
                    conf.permitAll();
                })
                .logout(conf -> conf
                        .logoutUrl("/auth/logout")
                        .logoutSuccessHandler(this::onLogoutSuccess)

                )

                .exceptionHandling(conf->conf
                        .authenticationEntryPoint(this::commence)
                        .accessDeniedHandler(this::onHandle))

                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(conf -> {
                    conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(jwtAuthorizedFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    //登录失败处理器
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(RestBean.error(401,"登录失败，密码错误").ToJSON());
    }

    //登录成功处理器
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        response.setContentType("application/json;charset=utf-8");
        User user = (User) authentication.getPrincipal();
        Account account = accountService.FindByName(user.getUsername());
        String token = util.createJWT(user,account.getAccountId());
        AuthorizeResp authorizeVo = new AuthorizeResp();
        authorizeVo.setName(account.getAccountUsername());
        authorizeVo.setRole(account.getAccountRole());
        authorizeVo.setToken(token);
        authorizeVo.setId(account.getAccountId());
        authorizeVo.setCreateTime(account.getRegisterTime());
        response.getWriter().write(RestBean.success(authorizeVo).ToJSON());
    }

    //未登录的报错
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(RestBean.error(401,"请先登录").ToJSON());
    }

    //没权限登录
    public void onHandle(HttpServletRequest request,
                         HttpServletResponse response,
                         AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(RestBean.forbidden(403).ToJSON());
    }

    //退出成功
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();

        String Header = request.getHeader("Authorization");
        if(util.invalidateJwt(Header)){
            writer.write(RestBean.success(new LogoutResp("登录成功")).ToJSON());
        }else{
            writer.write(RestBean.error(403,"退出失败").ToJSON());
        }
    }
}


