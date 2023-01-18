package com.procedure.manager.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSearchProcedureMonthVo {

    private Integer month;

    private Integer year;

    private Long userId;

}
