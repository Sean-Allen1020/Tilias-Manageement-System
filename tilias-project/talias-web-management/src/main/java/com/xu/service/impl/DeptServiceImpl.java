package com.xu.service.impl;

import com.xu.mapper.DeptMapper;
import com.xu.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    DeptMapper deptMapper;

    /**
     * 部门列表查询
     *
     * @return
     */
    public List findAll() {
        return deptMapper.findAll();
    }
}
