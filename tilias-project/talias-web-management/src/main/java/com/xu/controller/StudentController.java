package com.xu.controller;

import com.xu.result.Result;
import com.xu.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xu.pojo.dto.StudentDTO;
@RestController
@RequestMapping("/students")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 分页查询学生
     * @param studentDTO
     * @return
     */
    @GetMapping
    public Result studentPageQuery(StudentDTO studentDTO){
        log.info("分页查询学生: {}", studentDTO);

        return Result.success(studentService.studentPageQuery(studentDTO));
    }
}
