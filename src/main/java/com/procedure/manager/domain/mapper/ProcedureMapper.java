package com.procedure.manager.domain.mapper;

import com.procedure.manager.domain.model.ProcedureModel;
import com.procedure.manager.domain.request.DataToCreateProcedureRequest;
import com.procedure.manager.domain.response.ProcedureResponse;
import com.procedure.manager.domain.vo.ProcedureCreationDataVo;
import com.procedure.manager.domain.vo.ProcedureVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProcedureMapper {

    ProcedureModel voToModel(ProcedureVo procedureVo);

    ProcedureCreationDataVo requestToVo(DataToCreateProcedureRequest dataToCreateProcedureRequest);

    List<ProcedureResponse> voToResponseList(List<ProcedureVo> procedureVoList);

    @Mapping(source = "procedureType.name", target = "procedureTypeName")
    @Mapping(source = "value", target = "procedureValue")
    ProcedureResponse voToResponse(ProcedureVo procedureVo);

    List<ProcedureVo> modelListToVoList(List<ProcedureModel> procedureModelList);

    ProcedureVo modelToVo(ProcedureModel procedureModel);

}
