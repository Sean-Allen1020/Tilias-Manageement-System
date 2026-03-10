package com.xu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpDTO {

    private String username; // 用户名

    private String name; // 姓名

    private Integer gender; // 性别，1 男，2 女

    private String phone;

    private String image; // 图像

    private Integer deptId; // 部门id

    private LocalDate entryDate; // 入职日期

    private Integer job; // 职位，1 班主任，2 讲师，3 学工主管，4 教研主管，5 咨询师

    private Integer salary; // 薪资

    private List<EmpExprDTO> exprList; // 工作经历列表
}
