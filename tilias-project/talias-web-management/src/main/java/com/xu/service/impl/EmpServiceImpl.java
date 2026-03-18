package com.xu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xu.mapper.EmpMapper;
import com.xu.pojo.dto.EmpDTO;
import com.xu.pojo.dto.EmpExprDTO;
import com.xu.pojo.dto.EmpPageQueryDTO;
import com.xu.pojo.entity.Emp;
import com.xu.pojo.entity.EmpExpr;
import com.xu.result.PageResult;
import com.xu.service.EmpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
            exprList.forEach(e -> e.setEmpId(emp.getId()));
            empMapper.batchInsertExpr(exprList);
        }
    }

    /**
     * 删除员工
     *
     * @param ids
     */
    public void empDelete(List<Integer> ids) {
        empMapper.deleteById(ids);
        empMapper.deleteEmpExpr(ids);
    }

    /**
     * 根据id查询员工
     *
     * @param empId
     * @return
     */
    public Emp getById(Integer empId) {
        Emp emp = empMapper.getById(empId);
        List<EmpExpr> empExprList = empMapper.getEmpExprById(empId);
        emp.setExprList(empExprList);

        return emp;
    }

    /**
     * 修改员工信息
     *
     * @param empDTO
     */
    @Transactional
    public void empUpdate(EmpDTO empDTO) {
        Emp emp = new Emp();
        BeanUtils.copyProperties(empDTO, emp);
        empMapper.update(emp);

        // 删除员工原有工作经历
        Integer empId = emp.getId();
        List<Integer> empIds = new ArrayList<>();
        empIds.add(empId);
        empMapper.deleteEmpExpr(empIds);
        // 更新(添加)员工工作经历
        List<EmpExprDTO> exprList = empDTO.getExprList();
        if (exprList != null && !exprList.isEmpty()) {
            exprList.forEach(e -> e.setEmpId(empId));
            empMapper.batchInsertExpr(exprList);
        }
    }

    /**
     * 获取所有员工信息
     */
    public List<Emp> getAllEmp() {
        return empMapper.getAllEmp();
    }
}
