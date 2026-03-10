package com.xu.service.impl;

import com.xu.mapper.ReportMapper;
import com.xu.pojo.EmpReport;
import com.xu.pojo.EmpReportVO;
import com.xu.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportMapper reportMapper;

    /**
     * 获取员工相关统计数据
     * @return
     */
    public EmpReportVO getEmpJobData() {
        List<EmpReport> empJobDataList = reportMapper.getEmpJobData();

        List<String> jobList = empJobDataList.stream().map(empJobData -> empJobData.getJob()).toList();
        List<Integer> dataList = empJobDataList.stream().map(empReport -> empReport.getData()).toList();

        return new EmpReportVO(jobList, dataList);
    }

    /**
     * 获取员工性别数据
     * @return
     */
    public List<EmpReport> getEmpGenderData(){
        return reportMapper.getEmpGenderData();
    }
}
