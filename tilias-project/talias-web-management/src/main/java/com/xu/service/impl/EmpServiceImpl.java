package com.xu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xu.mapper.EmpMapper;
import com.xu.pojo.Emp;
import com.xu.pojo.EmpDTO;
import com.xu.pojo.EmpExprDTO;
import com.xu.pojo.EmpPageQueryDTO;
import com.xu.result.PageResult;
import com.xu.service.EmpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    /**
     * 员工分页查询
     *
     * @param empPageQueryDTO
     * @return
     */
    public PageResult empPageQuery(EmpPageQueryDTO empPageQueryDTO) {

        // 1. 设置分页参数
        PageHelper.startPage(empPageQueryDTO.getPage(), empPageQueryDTO.getPageSize());

        // 2. 调用Mapper执行查询：Page 本质上是一个 List（但携带 total 等分页信息）
        Page<Emp> page = empMapper.empQuery(empPageQueryDTO);

        // 3. 封装并返回结果
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 新增员工
     *
     * @param empDTO
     */
    @Transactional
    public void empAdd(EmpDTO empDTO) {
        // 设置默认入职时间为创建当天
        if (empDTO.getEntryDate() == null) {
            empDTO.setEntryDate(LocalDate.now());
        }
        // 将DTO属性值复制到实体类
        Emp emp = new Emp();
        BeanUtils.copyProperties(empDTO, emp);

        empMapper.insertEmp(emp);

        List<EmpExprDTO> exprList = empDTO.getExprList();
        if (exprList != null && !exprList.isEmpty()) {
            exprList.forEach(e -> {
                e.setEmpId(emp.getId());
            });
            empMapper.batchInsertExpr(exprList);
        }
    }
}
