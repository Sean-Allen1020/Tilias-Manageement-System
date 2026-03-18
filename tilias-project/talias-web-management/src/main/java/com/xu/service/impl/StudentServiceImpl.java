package com.xu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xu.mapper.StudentMapper;
import com.xu.result.PageResult;
import com.xu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    /**
     * 分页查询学生
     * @param studentDTO
     * @return
     */
    public Page studentPageQuery(StudentDTO studentDTO) {
        PageHelper.startPage(studentDTO.getPage(), studentDTO.getPageSize());

        Page<Student> page = studentMapper.studentPageQuery(studentDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }
}
