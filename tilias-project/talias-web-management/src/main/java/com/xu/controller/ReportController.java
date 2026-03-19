package com.xu.controller;

import com.xu.result.Result;
import com.xu.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@Slf4j
public class ReportController {

    @Autowired
    ReportService reportService;

    /**
     * 获取员工相关统计数据
     * @return
     */
    @GetMapping("/empJobData")
    public Result empJobData() {
      log.info("获取员工职位统计数据");
      return Result.success(reportService.getEmpJobData());
    }

    /**
     * 获取员工性别统计数据
     * @return
     */
    @GetMapping("/empGenderData")
    public Result empGenderData() {
        log.info("获取员工性别统计数据");
        return Result.success(reportService.getEmpGenderData());
    }

    /**
     * 获取学员学位统计数据
     * @return
     */
    @GetMapping("/studentDegreeData")
    public Result studentDegreeData() {
        log.info("获取学员学位统计数据");
        return Result.success(reportService.getStudentDegreeData());
    }

    /**
     * 获取学员班级统计数据
     * @return
     */
    @GetMapping("/studentCountData")
    public Result studentClazzData() {
        log.info("获取学员班级统计数据");
        return Result.success(reportService.getStudentClazzData());
    }
}
