package com.xu.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpPageQueryDTO implements Serializable {
    private static final Long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别，1 男，2 女
     */
    private Integer gender;

    /**
     * 范围匹配开始时间（入职日期）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;

    /**
     * 范围匹配结束时间（入职日期）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;

    /**
     * 页码，默认 1
     */
    private Integer page = 1;

    /**
     * 每页记录数，默认 10
     */
    private Integer pageSize = 10;
}
