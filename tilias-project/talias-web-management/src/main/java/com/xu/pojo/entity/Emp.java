package com.xu.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp implements Serializable {
    private static final Long serialVersionUID = 1L;
    /**
     * ID，主键
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别，1:男，2:女
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 职位，1 班主任，2 讲师，3 学工主管，4 教研主管，5 咨询师
     */
    private Integer job;

    /**
     * 薪资
     */
    private Integer salary;

    /**
     * 头像
     */
    private String image;

    /**
     * 入职日期
     */
    private LocalDate entryDate;

    /**
     * 部门ID
     */
    private Integer deptId;

    /**
     * 部门名
     */
    private String deptName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    private List<EmpExpr> exprList;
}
