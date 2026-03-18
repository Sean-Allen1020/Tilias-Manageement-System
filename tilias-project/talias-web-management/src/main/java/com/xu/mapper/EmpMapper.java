package com.xu.mapper;

import com.github.pagehelper.Page;
import com.xu.aspect.AutoFill;
import com.xu.pojo.dto.EmpExprDTO;
import com.xu.pojo.dto.EmpPageQueryDTO;
import com.xu.pojo.entity.Emp;
import com.xu.pojo.entity.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpMapper {

    /**
     * 员工分页查询
     * @param empPageQueryDTO
     * @return
     */
    Page<Emp> empQuery(EmpPageQueryDTO empPageQueryDTO);

    /**
     * 新增员工
     * @param emp
     */
    @AutoFill(type = "insert")
    void insertEmp(Emp emp);

    /**
     * 批量添加员工经历
     * @param exprList
     */
    void batchInsertExpr(List<EmpExprDTO> exprList);

    /**
     * 删除员工
     * @param ids
     */
    void deleteById(List<Integer> ids);

    /**
     * 删除员工工作经历
     * @param empIds
     */
    void deleteEmpExpr(List<Integer> empIds);

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    Emp getById(Integer id);

    /**
     * 根据id获取员工经历
     * @param empId
     * @return
     */
    List<EmpExpr> getEmpExprById(Integer empId);

    /**
     * 修改员工信息
     * @param emp
     */
    @AutoFill(type = "update")
    void update(Emp emp);


}
