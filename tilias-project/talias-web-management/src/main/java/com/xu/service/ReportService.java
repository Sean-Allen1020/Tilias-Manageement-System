package com.xu.service;

import com.xu.pojo.EmpReport;
import com.xu.pojo.EmpReportVO;

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
}
