package com.xu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpExpr implements Serializable {
    private static final Long serialVersionUID = 1L;

    /**
     * ID，主键
     */
    private Integer id;

    /**
     * 员工ID
     */
    private Integer empId;

    /**
     * 开始时间
     */
    private LocalDate begin;

    /**
     * 结束时间
     */
    private LocalDate end;

    /**
     * 公司名称
     */
    private String company;

    /**
     * 职位
     */
    private String job;
}
