package com.xu.service.impl;

import com.xu.mapper.DeptMapper;
import com.xu.pojo.entity.Dept;
import com.xu.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    /**
     * 部门列表查询
     *
     * @return
     */
    public List findAll() {
        return deptMapper.findAll();
    }

    /**
     * 根据id删除部门
     * @param deptId
     */
    public void deleteById(Integer deptId) {
        deptMapper.deleteById(deptId);
    }

    /**
     * 新增部门
     * @param dept
     */
    public void add(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());

        deptMapper.insert(dept);
    }

    /**
     * 根据id查询部门
     * @param deptId
     * @return
     */
    public Dept getById(Integer deptId) {
        return deptMapper.getById(deptId);
    }

    /**
     * 修改部门
     * @param dept
     */
    public void update(Dept dept) {
        deptMapper.update(dept);
    }
}
