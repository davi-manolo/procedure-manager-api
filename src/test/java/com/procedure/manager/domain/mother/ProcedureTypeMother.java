package com.procedure.manager.domain.mother;

import com.procedure.manager.domain.model.ProcedureTypeModel;
import com.procedure.manager.domain.vo.ProcedureTypeVo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcedureTypeMother {

    public static ProcedureTypeVo getProcedureTypeVo() {
        ProcedureTypeVo procedureTypeVo = new ProcedureTypeVo();
        procedureTypeVo.setProcedureTypeId(1L);
        procedureTypeVo.setName("Tipo de Procedimento");
        procedureTypeVo.setPercentage(30.00);
        procedureTypeVo.setDisabled(FALSE);
        return  procedureTypeVo;
    }

    public static ProcedureTypeModel getProcedureTypeModel() {
        ProcedureTypeModel procedureTypeModel = new ProcedureTypeModel();
        procedureTypeModel.setProcedureTypeId(1L);
        procedureTypeModel.setName("Tipo de Procedimento");
        procedureTypeModel.setPercentage(30.00);
        procedureTypeModel.setDisabled(FALSE);
        return  procedureTypeModel;
    }

    public static ProcedureTypeModel getProcedureTypeModelWithDisableTrue() {
        ProcedureTypeModel procedureTypeModel = getProcedureTypeModel();
        procedureTypeModel.setDisabled(TRUE);
        return procedureTypeModel;
    }

    public static ProcedureTypeModel getProcedureTypeEditedModel() {
        ProcedureTypeModel procedureTypeModel = new ProcedureTypeModel();
        procedureTypeModel.setProcedureTypeId(1L);
        procedureTypeModel.setName("Tipo de Procedimento Editado");
        procedureTypeModel.setPercentage(50.00);
        procedureTypeModel.setDisabled(FALSE);
        return  procedureTypeModel;
    }

    public static ProcedureTypeModel getProcedureTypeDisabledModel() {
        ProcedureTypeModel procedureTypeModel = getProcedureTypeModel();
        procedureTypeModel.setDisabled(TRUE);
        return  procedureTypeModel;
    }

    public static Optional<ProcedureTypeModel> getProcedureTypeModelOptional() {
        return Optional.of(getProcedureTypeModel());
    }

    public static Optional<ProcedureTypeModel> getProcedureTypeModelOptionalWithDisableTrue() {
        return Optional.of(getProcedureTypeModelWithDisableTrue());
    }

    public static Optional<ProcedureTypeModel> getEmptyProcedureTypeModelOptional() {
        return Optional.empty();
    }

    public static Optional<List<ProcedureTypeModel>> getProcedureTypeModelListOptional() {
        return Optional.of(List.of(getProcedureTypeModel()));
    }

    public static Optional<List<ProcedureTypeModel>> getEmptyProcedureTypeModelListOptional() {
        return Optional.empty();
    }

    public static List<ProcedureTypeVo> getProcedureTypeVoList() {
        return List.of(getProcedureTypeVo());
    }

}
