package com.xu.mapper;

import com.xu.pojo.entity.EmpReport;
import com.xu.pojo.entity.StudentReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 获取学员学位统计数据
     * @return
     */
    List<StudentReport> getStudentDegreeData();

    /**
     * 获取学员学位统计数据
     * @return
     */
    List<StudentReport> getStudentClazzData();
}
