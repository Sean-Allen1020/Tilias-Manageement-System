package com.xu.service;

import com.xu.pojo.entity.Dept;

import java.util.List;

public interface DeptService {

    /**
     * 部门列表查询
     * @return
     */
    List findAll();

    /**
     * 根据id删除部门
     * @param deptId
     */
    void deleteById(Integer deptId);

    /**
     * 新增部门
     * @param dept
     */
    void add(Dept dept);

    /**
     * 根据id查询部门
     * @param deptId
     * @return
     */
    Dept getById(Integer deptId);

    /**
     * 修改部门
     * @param dept
     */
    void update(Dept dept);
}
