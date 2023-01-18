package com.procedure.manager.domain.mapper;

import com.procedure.manager.domain.model.ProcedureModel;
import com.procedure.manager.domain.request.DataToCreateProcedureRequest;
import com.procedure.manager.domain.response.ProcedureResponse;
import com.procedure.manager.domain.vo.ProcedureCreationDataVo;
import com.procedure.manager.domain.vo.ProcedureForFileVo;
import com.procedure.manager.domain.vo.ProcedureVo;
import com.procedure.manager.util.DateUtils;
import com.procedure.manager.util.FormatUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = { FormatUtils.class, DateUtils.class })
public interface ProcedureMapper {

    ProcedureModel voToModel(ProcedureVo procedureVo);

    ProcedureCreationDataVo requestToVo(DataToCreateProcedureRequest dataToCreateProcedureRequest);

    List<ProcedureResponse> voToResponseList(List<ProcedureVo> procedureVoList);

    @Mapping(source = "procedureType.name", target = "procedureTypeName")
    @Mapping(source = "value", target = "procedureValue")
    ProcedureResponse voToResponse(ProcedureVo procedureVo);

    @Mapping(source = "customer", target = "procedureCustomer")
    @Mapping(source = "procedureType.name", target = "procedureType")
    @Mapping(target = "procedureDate", expression = "java(DateUtils.getLocalDateFormatted(procedureVo.getProcedureDate()))")
    @Mapping(target = "procedureValue", expression = "java(FormatUtils.convertToMonetaryValue(procedureVo.getValue()))")
    @Mapping(target = "valueReceived", expression = "java(FormatUtils.convertToMonetaryValue(procedureVo.getValueReceived()))")
    ProcedureForFileVo procedureVoToProcedureForFileVo(ProcedureVo procedureVo);

    List<ProcedureVo> modelListToVoList(List<ProcedureModel> procedureModelList);

    ProcedureVo modelToVo(ProcedureModel procedureModel);

}
