package com.xu.utils;

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
}
