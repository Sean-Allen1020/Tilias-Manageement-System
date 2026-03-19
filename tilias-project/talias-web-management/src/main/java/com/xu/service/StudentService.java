package com.xu.service;

import com.xu.pojo.dto.StudentDTO;
import com.xu.pojo.dto.StudentPageQueryDTO;
import com.xu.pojo.entity.Student;
import com.xu.result.PageResult;

import java.util.List;

public interface StudentService {

    /**
     * 分页查询学生
     * @param studentPageQueryDTO
     * @return
     */
    PageResult studentPageQuery(StudentPageQueryDTO studentPageQueryDTO);

    /**
     * 删除学员
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 新增学员
     * @param studentDTO
     */
    void addStudent(StudentDTO studentDTO);

    /**
     * 根据id查询学员
     * @param id
     * @return
     */
    Student getStudentById(Integer id);

    /**
     * 修改学员信息
     * @param studentDTO
     */
    void updateStudent(StudentDTO studentDTO);

    /**
     * 违纪处理
     * @param id
     * @param score
     */
    void updateStudentViolation(Integer id, Integer score);
}
