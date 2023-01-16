package com.procedure.manager.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureVo {

    private Long procedureId;
    private UserVo user;
    private LocalDate procedureDate;
    private String customer;
    private BigDecimal value;
    private ProcedureTypeVo procedureType;
    private BigDecimal valueReceived;
    private LocalDateTime registrationDate;
    private Boolean disabled;

}
