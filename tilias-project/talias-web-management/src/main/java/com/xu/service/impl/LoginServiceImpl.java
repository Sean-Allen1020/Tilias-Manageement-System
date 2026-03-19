package com.xu.service.impl;

import com.xu.exception.BusinessException;
import com.xu.mapper.LoginMapper;
import com.xu.pojo.dto.LoginDTO;
import com.xu.pojo.entity.Emp;
import com.xu.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    /**
     * 登录
     * @param loginDTO
     * @return
     */
    public Emp login(LoginDTO loginDTO) {

        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        // 校验用户名，并获取用户信息
        Emp emp = loginMapper.getByUsername(username);
        if (emp == null) {
            throw new BusinessException("用户不存在");
        }
        if (!password.equals(emp.getPassword())) {
            throw new BusinessException("密码错误");
        }
        return emp;
    }
}
