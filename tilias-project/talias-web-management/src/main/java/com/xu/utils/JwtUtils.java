package com.xu.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;

public class JwtUtils {
    /**
     * 创建Jwt令牌
     * @param claims
     * @param secretKeyStr
     * @param ttl
     * @return
     */
    public static String createJwt(Map claims, String secretKeyStr, Long ttl) {
        // 解码密钥
        SecretKey secretKey = hmacShaKeyFor(Decoders.BASE64.decode(secretKeyStr));

        String jwt = Jwts.builder()
                .claims(claims)
                .signWith(secretKey)
                .expiration(new Date(System.currentTimeMillis() + ttl))
                .compact();

        return jwt;
    }

    public static Claims parseJwt(String token, String secretKeyStr) {

        // 解码密钥
        SecretKey secretKey = hmacShaKeyFor(Decoders.BASE64.decode(secretKeyStr));

        // 最基础的解析令牌的方式，通过核对密钥，以及判断是否过期来验证令牌的合法性
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)  // 创建用于验证的密钥
                .build()
                .parseClaimsJws(token)  //解析令牌
                .getPayload();

        return claims;
    }
}
