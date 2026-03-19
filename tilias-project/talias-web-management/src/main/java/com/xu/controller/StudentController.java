package com.xu.controller;

import com.xu.pojo.dto.StudentDTO;
import com.xu.result.Result;
import com.xu.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.xu.pojo.dto.StudentPageQueryDTO;

import java.util.List;

@RestController
@RequestMapping("/students")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 分页查询学生
     * @param studentPageQueryDTO
     * @return
     */
    @GetMapping
    public Result studentPageQuery(StudentPageQueryDTO studentPageQueryDTO){
        log.info("分页查询学生: {}", studentPageQueryDTO);

        return Result.success(studentService.studentPageQuery(studentPageQueryDTO));
    }

    /**
     * 删除学员
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public Result studentDeleteByIds(@PathVariable List<Integer> ids){
        log.info("删除学员：{}", ids);
        studentService.deleteByIds(ids);
        return Result.success();
    }

    /**
     * 新增学员
     * @param studentDTO
     * @return
     */
    @PostMapping
    public Result addStudent(@RequestBody StudentDTO studentDTO){
        log.info("新增学员：{}", studentDTO);
        studentService.addStudent(studentDTO);
        return Result.success();
    }

    /**
     * 根据id查询学员
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getStudentById(@PathVariable Integer id){
        log.info("根据id查询学员: {}", id);

        return Result.success(studentService.getStudentById(id));
    }

    /**
     * 修改学员信息
     * @param studentDTO
     * @return
     */
    @PutMapping
    public Result updateStudent(@RequestBody StudentDTO studentDTO){
        log.info("修改学员信息: {}", studentDTO);
        studentService.updateStudent(studentDTO);
        return  Result.success();
    }

    /**
     * 违纪处理
     * @param id
     * @param score
     * @return
     */
    @PutMapping("/violation/{id}/{score}")
    public Result updateStudentViolation(@PathVariable Integer id, @PathVariable Integer score){
        log.info("违纪学员：{}", id);
        log.info("违纪分数：{}", score);
        studentService.updateStudentViolation(id, score);
        return  Result.success();
    }
}
