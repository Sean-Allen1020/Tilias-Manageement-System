package com.xu.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClazzPageQueryDTO {

    private String name;
    private LocalDate begin;
    private LocalDate end;

    private Integer page;
    private Integer pageSize;
}
