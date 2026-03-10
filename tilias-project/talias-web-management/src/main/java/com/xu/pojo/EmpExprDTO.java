package com.xu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpExprDTO {

    private Integer empId;   // 员工id

    private String company; // 所在公司

    private String job; // 职位

    private LocalDate begin; // 开始时间

    private LocalDate end; // 结束时间
}
