package com.procedure.manager.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureForFileVo {

    private String procedureDate;
    private String procedureCustomer;
    private String procedureValue;
    private String procedureType;
    private String valueReceived;

}
