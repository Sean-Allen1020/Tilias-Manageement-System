package com.xu.mapper;

import com.xu.pojo.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptMapper {
    /**
     * 查询部门列表
     * @return
     */
    List<Dept> findAll();
}
