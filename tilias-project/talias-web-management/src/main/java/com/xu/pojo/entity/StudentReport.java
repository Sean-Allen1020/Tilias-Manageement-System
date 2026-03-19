package com.xu.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentReport {

    // 学位和对应的人数
    private String name;
    private Integer value;

    // 班级和对应的人数
    private String clazz;
    private Integer data;
}
