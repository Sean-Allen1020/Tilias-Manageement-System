package com.xu.controller;

import com.xu.pojo.entity.Dept;
import com.xu.result.Result;
import com.xu.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/depts")
@Slf4j
public class DeptController {

    @Autowired
    DeptService deptService;

    /**
     * 部门列表查询
     * @return
     */
    @GetMapping
    public Result list(){
        log.info("执行部门列表查询");
        return Result.success(deptService.findAll());
    }

    /**
     * 根据id删除部门
     * @param deptId
     * @return
     */
    @DeleteMapping
    public Result delete(@RequestParam("id") Integer deptId){
        log.info("执行部门删除：" + deptId);
        deptService.deleteById(deptId);
        return Result.success();
    }

    /**
     * 新增部门
     * @param dept
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Dept dept){
        log.info("新增部门：" + dept.getName());
        deptService.add(dept);
        return Result.success();
    }

    /**
     * 根据id查询部门
     * @param deptId
     * @return
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable("id") Integer deptId){
        log.info("根据id查询部门：" + deptId);
        return Result.success(deptService.getById(deptId));
    }

    /**
     * 修改部门
     * @param dept
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Dept dept){
        log.info("部门id：" + dept.getId() + "\n修改部门名为：" + dept.getName());
        deptService.update(dept);
        return Result.success();
    }
}
