package com.xu.service;

import com.xu.pojo.Emp;
import com.xu.pojo.EmpDTO;
import com.xu.pojo.EmpPageQueryDTO;
import com.xu.result.PageResult;

import java.util.List;

public interface EmpService {

    /**
     * 员工分页查询
     * @param empPageQueryDTO
     * @return
     */
    PageResult empPageQuery(EmpPageQueryDTO empPageQueryDTO);

    /**
     * 新增员工
     * @param empDTO
     */
    void empAdd(EmpDTO empDTO);
}
