package com.xu.service.impl;

import com.xu.mapper.ReportMapper;
import com.xu.mapper.StudentMapper;
import com.xu.pojo.EmpReportVO;
import com.xu.pojo.StudentReportVO;
import com.xu.pojo.entity.EmpReport;
import com.xu.pojo.entity.StudentReport;
import com.xu.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportMapper reportMapper;
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 获取员工相关统计数据
     * @return
     */
    public EmpReportVO getEmpJobData() {
        List<EmpReport> empJobDataList = reportMapper.getEmpJobData();

        List<String> jobList = empJobDataList.stream().map(EmpReport::getJob).toList();
        List<Integer> dataList = empJobDataList.stream().map(EmpReport::getData).toList();

        return new EmpReportVO(jobList, dataList);
    }

    /**
     * 获取员工性别数据
     * @return
     */
    public List<EmpReport> getEmpGenderData(){
        return reportMapper.getEmpGenderData();
    }

    /**
     * 获取学员学位统计数据
     * @return
     */
    public List<StudentReport> getStudentDegreeData() {

        return reportMapper.getStudentDegreeData();
    }

    /**
     * 获取学员学位统计数据
     * @return
     */
    public StudentReportVO getStudentClazzData() {
        List<StudentReport> studentClazzData = reportMapper.getStudentClazzData();

        List<String> clazzList = studentClazzData.stream().map(StudentReport::getClazz).toList();
        List<Integer> dataList = studentClazzData.stream().map(StudentReport::getData).toList();

        return new StudentReportVO(clazzList, dataList);
    }
}
