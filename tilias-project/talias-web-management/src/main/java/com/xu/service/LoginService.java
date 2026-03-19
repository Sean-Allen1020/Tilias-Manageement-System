package com.xu.service;

import com.xu.pojo.dto.LoginDTO;
import com.xu.pojo.entity.Emp;

public interface LoginService {

    /**
     * 登录
     * @param loginDTO
     * @return
     */
    Emp login(LoginDTO loginDTO);
}
