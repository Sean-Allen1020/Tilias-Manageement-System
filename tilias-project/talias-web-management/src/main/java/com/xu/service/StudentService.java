package com.xu.service;

import com.github.pagehelper.Page;

public interface StudentService {

    /**
     * 分页查询学生
     * @param studentDTO
     * @return
     */
    Page studentPageQuery(StudentDTO studentDTO);
}
