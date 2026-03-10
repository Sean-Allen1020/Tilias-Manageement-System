package com.xu.controller;

import com.xu.pojo.Emp;
import com.xu.pojo.EmpDTO;
import com.xu.pojo.EmpPageQueryDTO;
import com.xu.result.Result;
import com.xu.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emps")
@Slf4j
public class EmpController {

    @Autowired
    private EmpService empService;

    /**
     * 员工分页查询
     * @param empPageQueryDTO
     * @return
     */
    @GetMapping
    public Result empPageQuery(EmpPageQueryDTO empPageQueryDTO) {
        log.info("员工查询{}", empPageQueryDTO);
        return Result.success(empService.empPageQuery(empPageQueryDTO));
    }

    /**
     * 新增员工
     * @param empDTO
     * @return
     */
    @PostMapping
    public Result empAdd(@RequestBody EmpDTO empDTO) {
        log.info("新增员工：{}", empDTO.getName());
        empService.empAdd(empDTO);
        return Result.success();
    }


}
