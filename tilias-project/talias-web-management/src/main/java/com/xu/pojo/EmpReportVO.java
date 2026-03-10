package com.xu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpReportVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> jobList;
    private List<Integer> dataList;
}
