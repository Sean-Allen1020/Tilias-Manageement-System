package com.xu.mapper;

import com.xu.pojo.dto.LoginDTO;
import com.xu.pojo.entity.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {
    /**
     * 登录验证
     * @param username
     * @return
     */
    @Select("select * from emp where username = #{username}")
    Emp getByUsername(String username);
}
