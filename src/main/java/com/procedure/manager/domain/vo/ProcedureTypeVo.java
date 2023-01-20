package com.procedure.manager.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureTypeVo {

    private Long procedureTypeId;
    private UserVo user;
    private String name;
    private Double percentage;
    private Boolean disabled;

}
