package com.procedure.manager.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureCreationDataVo {

    private LocalDate procedureDate;
    private String customer;
    private BigDecimal value;
    private Long procedureTypeId;
    private Long userId;

}
