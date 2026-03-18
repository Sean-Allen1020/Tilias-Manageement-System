package com.xu.service;

import com.xu.pojo.dto.ClazzDTO;
import com.xu.pojo.dto.ClazzPageQueryDTO;
import com.xu.pojo.entity.Clazz;
import com.xu.result.PageResult;

import java.util.List;

public interface ClazzService {

    /**
     * 班级分页查询
     * @param clazzPageQueryDTO
     * @return
     */
    PageResult clazzPageQuery(ClazzPageQueryDTO clazzPageQueryDTO);

    /**
     * 根据id查询班级
     * @param id
     * @return
     */
    Clazz getClazzById(Integer id);

    /**
     * 修改班级信息
     * @param clazzDTO
     */
    void updateClazz(ClazzDTO clazzDTO);

    /**
     * 查询所有班级信息
     * @return
     */
    List getAllClazz();
}
