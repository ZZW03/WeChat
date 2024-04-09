package com.zzw.Filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.zzw.Utils.JwtUtil;
import com.zzw.common.Const;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthorizedFilter extends OncePerRequestFilter {

    @Resource
    JwtUtil util;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 对前端请求的头文件 进行验证
        // 从header中获得JWT
        String Authorization = request.getHeader("Authorization");
        DecodedJWT jwt = util.resolveJwt(Authorization);
        //判断JWT是否正确
        if(jwt != null) {
            UserDetails user = util.toUser(jwt);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.setAttribute(Const.ACCOUNT.ATTR_USER_ID, util.toId(jwt));
        }
        // 继续传递请求给下一个过滤器
        filterChain.doFilter(request, response);
    }


}
