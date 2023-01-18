package com.procedure.manager.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataContentForFileVo {

    private String totalReceived;
    private List<ProcedureForFileVo> procedureList;

}
