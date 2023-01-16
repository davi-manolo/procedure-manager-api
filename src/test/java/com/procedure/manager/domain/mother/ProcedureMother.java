package com.procedure.manager.domain.mother;

import com.procedure.manager.domain.model.ProcedureModel;
import com.procedure.manager.domain.vo.ProcedureCreationDataVo;
import com.procedure.manager.domain.vo.ProcedureVo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static com.procedure.manager.domain.mother.ProcedureTypeMother.getProcedureTypeModel;
import static com.procedure.manager.domain.mother.ProcedureTypeMother.getProcedureTypeVo;
import static com.procedure.manager.domain.mother.UserMother.getUserModel;
import static com.procedure.manager.domain.mother.UserMother.getUserVo;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcedureMother {

    public static ProcedureCreationDataVo getDataToCreateProcedureVo() {
        ProcedureCreationDataVo procedureCreationDataVo = new ProcedureCreationDataVo();
        procedureCreationDataVo.setProcedureDate(LocalDate.of(2022, Month.SEPTEMBER, 5));
        procedureCreationDataVo.setCustomer("Cliente Beltrano");
        procedureCreationDataVo.setValue(BigDecimal.valueOf(800.00));
        procedureCreationDataVo.setProcedureTypeId(1L);
        procedureCreationDataVo.setUserId(1L);
        return procedureCreationDataVo;
    }

    public static ProcedureModel getProcedureModel() {
        ProcedureModel procedureModel = new ProcedureModel();
        procedureModel.setProcedureId(1L);
        procedureModel.setProcedureDate(LocalDate.of(2022, Month.SEPTEMBER, 5));
        procedureModel.setCustomer("Cliente Beltrano");
        procedureModel.setValue(BigDecimal.valueOf(800.00));
        procedureModel.setValueReceived(BigDecimal.valueOf(240.00));
        procedureModel.setProcedureType(getProcedureTypeModel());
        procedureModel.setRegistrationDate(LocalDateTime.of(2022, Month.SEPTEMBER, 5, 10, 20, 30));
        procedureModel.setUser(getUserModel());
        procedureModel.setDisabled(FALSE);
        return procedureModel;
    }

    public static ProcedureVo getProcedureVo() {
        ProcedureVo procedureVo = new ProcedureVo();
        procedureVo.setProcedureId(1L);
        procedureVo.setProcedureDate(LocalDate.of(2022, Month.SEPTEMBER, 5));
        procedureVo.setCustomer("Cliente Beltrano");
        procedureVo.setValue(BigDecimal.valueOf(800.00));
        procedureVo.setValueReceived(BigDecimal.valueOf(240.00));
        procedureVo.setProcedureType(getProcedureTypeVo());
        procedureVo.setRegistrationDate(LocalDateTime.of(2022, Month.SEPTEMBER, 5, 10, 20, 30));
        procedureVo.setUser(getUserVo());
        procedureVo.setDisabled(FALSE);
        return procedureVo;
    }

    public static ProcedureModel getProcedureDisabledModel() {
        ProcedureModel procedureModel = getProcedureModel();
        procedureModel.setDisabled(TRUE);
        return  procedureModel;
    }

    public static Optional<List<ProcedureModel>> getProcedureModelListOptional() {
        return Optional.of(List.of(getProcedureModel()));
    }

    public static Optional<List<ProcedureModel>> getEmptyProcedureModelListOptional() {
        return Optional.empty();
    }

    public static Optional<ProcedureModel> getProcedureModelOptional() {
        return Optional.of(getProcedureModel());
    }

    public static Optional<ProcedureModel> getEmptyProcedureModelOptional() {
        return Optional.empty();
    }

    public static List<ProcedureVo> getProcedureVoList() {
        return List.of(getProcedureVo());
    }

}
