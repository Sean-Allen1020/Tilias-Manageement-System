package com.xu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xu.exception.BusinessException;
import com.xu.mapper.ClazzMapper;
import com.xu.mapper.StudentMapper;
import com.xu.pojo.dto.ClazzDTO;
import com.xu.pojo.dto.ClazzPageQueryDTO;
import com.xu.pojo.entity.Clazz;
import com.xu.result.PageResult;
import com.xu.service.ClazzService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    ClazzMapper clazzMapper;
    @Autowired
    StudentMapper studentMapper;

    /**
     * 班级分页查询
     * @param clazzPageQueryDTO
     * @return
     */
    public PageResult clazzPageQuery(ClazzPageQueryDTO clazzPageQueryDTO) {
        PageHelper.startPage(clazzPageQueryDTO.getPage(), clazzPageQueryDTO.getPageSize());

        Page<Clazz> page = clazzMapper.clazzQuery(clazzPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id查询班级
     * @param id
     * @return
     */
    public Clazz getClazzById(Integer id) {
        return clazzMapper.getById(id);
    }

    /**
     * 修改班级信息
     * @param clazzDTO
     */
    public void updateClazz(ClazzDTO clazzDTO) {
        Clazz clazz = new Clazz();
        BeanUtils.copyProperties(clazzDTO, clazz);

        clazzMapper.update(clazz);
    }

    /**
     * 查询所有班级信息
     * @return
     */
    public List getAllClazz() {
        return clazzMapper.list();
    }

    /**
     * 删除班级
     * @param id
     */
    public void deleteById(Integer id) {
        Integer count = studentMapper.countByClazzId(id);
        if (count > 0) {
            throw new BusinessException("班级与学员关联，无法删除");
        }
        clazzMapper.deleteById(id);
    }

    /**
     * 添加班级
     * @param clazzDTO
     */
    public void addClazz(ClazzDTO clazzDTO) {
        Clazz clazz = new Clazz();
        BeanUtils.copyProperties(clazzDTO, clazz);

        clazzMapper.insertClazz(clazz);
    }
}
