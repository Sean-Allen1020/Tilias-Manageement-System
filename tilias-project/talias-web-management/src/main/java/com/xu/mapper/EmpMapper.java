package com.xu.mapper;

import com.github.pagehelper.Page;
import com.xu.aspect.AutoFill;
import com.xu.pojo.Emp;
import com.xu.pojo.EmpExprDTO;
import com.xu.pojo.EmpPageQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

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
}
