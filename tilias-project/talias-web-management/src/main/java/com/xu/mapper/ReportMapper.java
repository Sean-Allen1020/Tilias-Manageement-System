package com.xu.mapper;

import com.xu.pojo.EmpReport;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {
    /**
     * 获取员工职位统计数据
     * @return
     */
    List<EmpReport> getEmpJobData();

    /**
     * 获取员工性别数据
     * @return
     */
    List<EmpReport> getEmpGenderData();
}
