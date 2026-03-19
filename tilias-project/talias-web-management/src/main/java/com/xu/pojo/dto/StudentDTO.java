package com.xu.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    // id
    private Integer id;

    // 姓名
    private String name;

    // 学号
    private String no;

    // 性别
    private Integer gender;

    // 手机号
    private String phone;

    // 学历（1:初中，2:高中，3:大专，4:本科，5:硕士，6:博士）
    private Integer degree;

    // 班级ID
    private Integer clazzId;

    // 身份证号
    private String idCard;

    // 是否是院校学生（1:是，0:否）
    private Integer isCollege;

    // 联系地址
    private String address;

    // 毕业时间
    private LocalDate graduationDate;

    private Short violationCount;
    private Short violationScore;
}
