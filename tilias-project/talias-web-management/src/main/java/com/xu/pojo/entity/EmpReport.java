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

    private String job;
    private Integer data;

    private String gender;
    private Integer value;
}
