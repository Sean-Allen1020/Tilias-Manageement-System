package com.xu.controller;

import com.xu.pojo.dto.ClazzDTO;
import com.xu.pojo.dto.ClazzPageQueryDTO;
import com.xu.pojo.entity.Clazz;
import com.xu.result.PageResult;
import com.xu.result.Result;
import com.xu.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clazzs")
@Slf4j
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    /**
     * 班级分页查询
     * @param clazzPageQueryDTO
     * @return
     */
    @GetMapping
    public Result clazzPageQuery(ClazzPageQueryDTO clazzPageQueryDTO){
        log.info("分页查询班级: {}", clazzPageQueryDTO);

        return Result.success(clazzService.clazzPageQuery(clazzPageQueryDTO));
    }

    /**
     * 根据id查询班级
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getClazzById(@PathVariable Integer id){
        log.info("根据id查询班级: {}", id);
        return Result.success(clazzService.getClazzById(id));
    }

    /**
     * 修改班级信息
     * @param clazzDTO
     * @return
     */
    @PutMapping
    public Result updateClazz(@RequestBody ClazzDTO clazzDTO){
        log.info("修改班级信息: {}", clazzDTO);
        clazzService.updateClazz(clazzDTO);
        return Result.success();
    }

    /**
     * 查询所有班级信息
     * @return
     */
    @GetMapping("/list")
    public Result getAllClazz(){
        log.info("查询所有班级信息");
        return Result.success(clazzService.getAllClazz());
    }
}
