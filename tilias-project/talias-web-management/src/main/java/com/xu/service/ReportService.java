package com.xu.service;

import com.xu.pojo.EmpReportVO;
import com.xu.pojo.StudentReportVO;
import com.xu.pojo.entity.EmpReport;
import com.xu.pojo.entity.StudentReport;

import java.util.List;

public interface ReportService {
    /**
     * 获取员工相关统计数据
     * @return
     */
    EmpReportVO getEmpJobData();

    /**
     * 获取员工性别数据
     * @return
     */
    public List<EmpReport> getEmpGenderData();

    /**
     * 获取学员学位统计数据
     * @return
     */
    List<StudentReport> getStudentDegreeData();

    /**
     * 获取学员班级统计数据
     * @return
     */
    StudentReportVO getStudentClazzData();
}
