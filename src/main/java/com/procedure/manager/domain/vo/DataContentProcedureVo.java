package com.procedure.manager.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataContentProcedureVo {

    private String procedureDate;
    private String customer;
    private String procedureTypeName;
    private String procedureValue;
    private String receivedValue;

}
