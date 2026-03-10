package com.xu.controller;

import com.xu.result.Result;
import com.xu.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    DeptService deptService;

    /**
     * 部门列表查询
     * @return
     */
    @GetMapping
    public Result list(){
        System.out.println("执行部门列表查询");
        return Result.success(deptService.findAll());
    }
}
