package com.xu.interceptor;

import com.xu.properties.JwtProperties;
import com.xu.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 拦截器
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 拦截器实现方法
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        log.info("拦截验证开始");

        // 1. 判断拦截到的是否位 Controller方法
        if (!(handler instanceof HandlerMethod)) {
            // 非Controller方法，则为静态资源，放行
            return true;
        }
        log.info("拦截验证结束1");
        // 2. 从前端发送的请求头中获取token
        String token = request.getHeader("token");// 此处的 token 是依据前端来的，如果前端对令牌的变量名定义为其它的，则用其它的名字
        // 3. 对token判空
        if (token == null || token.isEmpty()) {
            // 向前端响应 401状态码
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        log.info("拦截验证结束2");
        // 4. 解析token，判断token合法性
        try {
            log.info("解析令牌: {}", token);
            Claims claims = JwtUtils.parseJwt(token, jwtProperties.getSecretKey());

            // 5. 拦截验证通过，放行
            log.info("令牌合法，放行");
            return true;
        }
        catch (ExpiredJwtException e) {
            // token 过期
            log.info("令牌过期");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        catch (SignatureException e) {
            // 签名不对，可能被篡改，或者密钥不匹配
            log.info("签名不对，可能被篡改，或者密钥不匹配");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        catch (JwtException | IllegalArgumentException e) {
            // 其余 JWT 非法情况
            log.info("其余Jwt非法情况");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
