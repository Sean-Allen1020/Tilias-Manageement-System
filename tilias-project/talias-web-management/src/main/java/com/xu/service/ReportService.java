package com.xu.service;

import com.xu.pojo.entity.EmpReport;

import java.util.List;

public interface ReportService {
    /**
     * 获取员工相关统计数据
     * @return
     */
    com.xu.pojo.EmpReportVO getEmpJobData();

    /**
     * 获取员工性别数据
     * @return
     */
    public List<EmpReport> getEmpGenderData();
}
