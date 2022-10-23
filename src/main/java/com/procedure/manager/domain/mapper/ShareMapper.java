package com.procedure.manager.domain.mapper;

import com.procedure.manager.domain.request.DataContentProcedureRequest;
import com.procedure.manager.domain.vo.DataContentProcedureVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShareMapper {

    List<DataContentProcedureVo> requestToVo(List<DataContentProcedureRequest> contentProcedureRequestList);

}
