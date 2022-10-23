package com.procedure.manager.domain.mapper;

import com.procedure.manager.domain.model.ProcedureTypeModel;
import com.procedure.manager.domain.request.ProcedureTypeRequest;
import com.procedure.manager.domain.response.ProcedureTypeResponse;
import com.procedure.manager.domain.vo.ProcedureTypeVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProcedureTypeMapper {

    ProcedureTypeModel voToModel(ProcedureTypeVo procedureTypeVo);

    ProcedureTypeVo requestToVo(ProcedureTypeRequest procedureTypeRequest);

    ProcedureTypeVo modelToVo(ProcedureTypeModel procedureTypeModel);

    ProcedureTypeResponse voToResponse(ProcedureTypeVo procedureType);

    List<ProcedureTypeVo> modelListToVoList(List<ProcedureTypeModel> procedureTypeModelList);

    List<ProcedureTypeResponse> voListToResponseList(List<ProcedureTypeVo> procedureTypeList);

}
