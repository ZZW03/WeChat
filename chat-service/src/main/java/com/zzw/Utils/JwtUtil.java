package com.zzw.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zzw.common.Const;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtil {

    @Resource
    StringRedisTemplate template;

    @Value("${spring.security.jwt.key}")
    public String Key;


    //用JWT保存数据
    public String createJWT(UserDetails userDetails,int userId){

        //先加密key
        Algorithm algorithm = Algorithm.HMAC256(Key);
        //设置 生成时间
        Calendar calendar =Calendar.getInstance();
        Date now = calendar.getTime();
        //设置 过期时间 在当前时间加上设置的时间 7天
        calendar.add(Calendar.SECOND, 3600 * 24 * 30);
        //创建对象
        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("username",userDetails.getUsername())
                .withClaim("id", userId)
                //返回一个权限对象的集合
                .withClaim("Authorization", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .withExpiresAt(calendar.getTime())  //设置过期时间
                .withIssuedAt(now)    //设置创建创建时间
                .sign(algorithm);   //最终签名
    }



    /**
     * 解析Jwt令牌
     * @param headerToken 请求头中携带的令牌
     * @return DecodedJWT
     */
    public DecodedJWT resolveJwt(String headerToken){
        String token = this.convertToken(headerToken);
        if(token == null) return null;
        Algorithm algorithm = Algorithm.HMAC256(Key);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT verify = jwtVerifier.verify(token);  //对JWT令牌进行验证，看看是否被修改
            if (this.CheckBlackList(verify.getId())){
                return  null;
            }
            //判断是否在黑名单中
            Map<String, Claim> claims = verify.getClaims();  //获取令牌中内容
            if(new Date().after(claims.get("exp").asDate())) //如果是过期令牌则返回null
                return null;
            return verify;
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    /**
     * 将jwt对象中的内容封装为UserDetails
     * @param jwt 已解析的Jwt对象
     * @return UserDetails
     */
    public UserDetails toUser(DecodedJWT jwt) {
        Map<String, Claim> claims = jwt.getClaims();
        return User
                .withUsername(claims.get("username").asString())
                .password("")
                .authorities(claims.get("Authorization").asArray(String.class))
                .build();
    }

    public Integer toId(DecodedJWT jwt) {
        Map<String, Claim> claims = jwt.getClaims();
        return claims.get("id").asInt();
    }

    //获取Jwt中的token
    private String convertToken(String headerToken){
        if(headerToken == null || !headerToken.startsWith("Bearer "))
            return null;
        return headerToken.substring(7);
    }

    //检查是否在redis里面
    public boolean CheckBlackList(String uuid){
        return Boolean.TRUE.equals(template.hasKey(Const.REDIS.BLACK_LIST + uuid));
    }

    //是否成功放入黑名单
    public boolean invalidateJwt(String HeaderToken){
        String token = this.convertToken(HeaderToken);
        if (token == null) {return false;}

        Algorithm algorithm = Algorithm.HMAC256(Key);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try{
            DecodedJWT jwt = jwtVerifier.verify(token);
            return deleteRedis(jwt.getId() , jwt.getExpiresAt());

        }catch (Exception e){
            return  false;
        }

    }

    //将JWT放入黑名单
    public boolean deleteRedis(String uuid ,Date time){
        try{
            if (CheckBlackList(uuid)){
                return  false;
            }
            //存入黑名单中 和 过期时间
            Date now = new Date();
            long expire = Math.max(time.getTime() - now.getTime(),0);
            template.opsForValue().set(Const.REDIS.BLACK_LIST + uuid, String.valueOf(expire));
            return  true;
        }catch (Exception e){
            System.out.println("错误");
        }
        return false;
    }


}
