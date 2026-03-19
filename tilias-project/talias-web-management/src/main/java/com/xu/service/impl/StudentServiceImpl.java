package com.xu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xu.mapper.StudentMapper;
import com.xu.pojo.dto.StudentDTO;
import com.xu.pojo.dto.StudentPageQueryDTO;
import com.xu.pojo.entity.Student;
import com.xu.result.PageResult;
import com.xu.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    /**
     * 分页查询学生
     * @param studentPageQueryDTO
     * @return
     */
    public PageResult studentPageQuery(StudentPageQueryDTO studentPageQueryDTO) {
        PageHelper.startPage(studentPageQueryDTO.getPage(), studentPageQueryDTO.getPageSize());

        Page<Student> page = studentMapper.studentPageQuery(studentPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 删除学员
     * @param ids
     */
    public void deleteByIds(List<Integer> ids) {
        Integer i = studentMapper.deleteByIds(ids);
        log.info("删除操作执行了: {}行", i);
    }

    /**
     * 新增学员
     * @param studentDTO
     */
    public void addStudent(StudentDTO studentDTO) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);

        student.setViolationCount((short) 0);
        student.setViolationScore((short) 0);

        studentMapper.insertStudent(student);
    }

    /**
     * 根据id查询学员
     * @param id
     * @return
     */
    public Student getStudentById(Integer id) {
        return studentMapper.getById(id);
    }

    /**
     * 修改学员信息
     * @param studentDTO
     */
    public void updateStudent(StudentDTO studentDTO) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);
        studentMapper.updateStudent(student);
    }

    /**
     * 违纪处理
     * @param id
     * @param score
     */
    public void updateStudentViolation(Integer id, Integer score) {

        Student student = studentMapper.getById(id);
        student.setViolationCount((short) (student.getViolationCount() + 1));
        student.setViolationScore((short) (student.getViolationScore() + score));

        studentMapper.updateStudent(student);
    }
}
