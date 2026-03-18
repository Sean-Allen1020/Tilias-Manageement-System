package com.xu.mapper;

import com.github.pagehelper.Page;
import com.xu.aspect.AutoFill;
import com.xu.pojo.dto.ClazzPageQueryDTO;
import com.xu.pojo.entity.Clazz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClazzMapper {

    /**
     * 班级分页查询
     * @param clazzPageQueryDTO
     * @return
     */
    Page<Clazz> clazzQuery(ClazzPageQueryDTO clazzPageQueryDTO);

    /**
     * 根据id查询班级
     * @param id
     * @return
     */
    @Select("select c.*, e.name masterName from clazz c left join emp e on c.master_id = e.id where c.id = #{id}")
    Clazz getById(Integer id);

    @AutoFill(type = "update")
    void update(Clazz clazz);

    /**
     * 查询所有班级信息
     * @return
     */
    @Select("select * from clazz")
    List<Clazz> list();
}
