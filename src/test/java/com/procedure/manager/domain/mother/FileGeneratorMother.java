package com.procedure.manager.domain.mother;

import com.procedure.manager.domain.vo.DataContentForFileVo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.procedure.manager.domain.mother.ProcedureMother.getProcedureForFileVoList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileGeneratorMother {

    public static DataContentForFileVo getDataContentForFileVo() {
        DataContentForFileVo dataContentForFileVo = new DataContentForFileVo();
        dataContentForFileVo.setMonth("1");
        dataContentForFileVo.setProcedureList(getProcedureForFileVoList());
        dataContentForFileVo.setTotalReceived("R$ 1.200,00");
        return dataContentForFileVo;
    }

}
