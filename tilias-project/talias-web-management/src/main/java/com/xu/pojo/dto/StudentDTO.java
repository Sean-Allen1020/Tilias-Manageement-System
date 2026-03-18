package com.xu.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    private Integer id;
    private String name;
    private Integer degree;
    private Integer clazzId;

    private Integer page;
    private Integer pageSize;
}
