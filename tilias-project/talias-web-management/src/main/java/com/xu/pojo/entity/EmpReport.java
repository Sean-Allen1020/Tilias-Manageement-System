package com.xu.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpReport implements Serializable {
    private static final long serialVersionUID = 1L;

    // 职位和对应的人数
    private String job;
    private Integer data;

    // 性别和对应的人数
    private String name;
    private Integer value;
}
