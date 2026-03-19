package com.xu.controller;

import com.xu.aspect.AutoFill;
import com.xu.pojo.LoginVO;
import com.xu.pojo.dto.LoginDTO;
import com.xu.pojo.entity.Emp;
import com.xu.properties.JwtProperties;
import com.xu.result.Result;
import com.xu.service.LoginService;
import com.xu.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param loginDTO
     * @return
     */
    @PostMapping
    public Result login(@RequestBody LoginDTO loginDTO) {

        // 登录验证
        Emp emp = loginService.login(loginDTO);

        // 登陆验证成功，生成令牌
        Map<String, String> claims = new HashMap<>();
        claims.put("id", String.valueOf(emp.getId()));
        claims.put("username", emp.getUsername());

        String token = JwtUtils.createJwt(claims, jwtProperties.getSecretKey(), jwtProperties.getTtl());

        LoginVO logVO = LoginVO.builder()
                .id(emp.getId())
                .username(emp.getUsername())
                .name(emp.getName())
                .token(token)
                .build();

        return Result.success(logVO);
    }
}
