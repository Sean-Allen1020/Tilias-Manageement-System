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

    @GetMapping("/empGenderData")
    public Result empGenderData() {
        log.info("获取员工性别统计数据");
        return Result.success(reportService.getEmpGenderData());
    }
}
