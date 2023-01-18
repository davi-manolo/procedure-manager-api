package com.procedure.manager.domain.mapper;

import com.procedure.manager.domain.request.DataSearchProcedureMonthRequest;
import com.procedure.manager.domain.vo.DataSearchProcedureMonthVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShareMapper {

    DataSearchProcedureMonthVo requestToVo(DataSearchProcedureMonthRequest dataSearchProcedureMonthRequest);

}
