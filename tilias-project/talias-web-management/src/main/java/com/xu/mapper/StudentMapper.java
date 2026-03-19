package com.xu.mapper;

import com.github.pagehelper.Page;
import com.xu.aspect.AutoFill;
import com.xu.pojo.dto.StudentPageQueryDTO;
import com.xu.pojo.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {
    /**
     * 学员关联班级统计
     * @param clazzId
     * @return
     */
    @Select("select count(*) from student where clazz_id = #{clazzId}")
    Integer countByClazzId(Integer clazzId);

    /**
     * 学生分页查询
     * @param studentPageQueryDTO
     * @return
     */
    Page<Student> studentPageQuery(StudentPageQueryDTO studentPageQueryDTO);

    /**
     * 删除学员
     * @param ids
     */
    Integer deleteByIds(List<Integer> ids);

    /**
     * 新增学员
     * @param student
     */
    @AutoFill(type = "insert")
    void insertStudent(Student student);

    /**
     * 根据id查询学员
     * @param id
     * @return
     */
    @Select("select * from student where id = #{id}")
    Student getById(Integer id);

    /**
     * 修改学员信息
     * @param student
     */
    @AutoFill(type = "update")
    void updateStudent(Student student);
}
