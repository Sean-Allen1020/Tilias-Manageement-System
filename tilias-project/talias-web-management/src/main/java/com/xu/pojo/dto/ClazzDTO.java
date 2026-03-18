package com.xu.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClazzDTO {

    private Integer id;
    private String name;
    private String room;
    private Integer masterId;
    private Integer subject;

    private LocalDate  beginDate;
    private LocalDate endDate;
}
