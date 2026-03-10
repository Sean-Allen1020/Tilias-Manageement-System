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

    /**
     * 删除员工
     * @param ids
     */
    void empDelete(List<Integer> ids);

    /**
     * 根据id查询员工
     * @param empId
     * @return
     */
    Emp getById(Integer empId);

    /**
     * 修改员工信息
     * @param empDTO
     */
    void empUpdate(EmpDTO empDTO);
}
