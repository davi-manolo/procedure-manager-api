package com.procedure.manager.domain.mapper;

import com.procedure.manager.domain.request.DataSearchProcedureMonthRequest;
import com.procedure.manager.domain.response.FileResponse;
import com.procedure.manager.domain.vo.DataSearchProcedureMonthVo;
import com.procedure.manager.domain.vo.FileVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShareMapper {

    DataSearchProcedureMonthVo requestToVo(DataSearchProcedureMonthRequest dataSearchProcedureMonthRequest);

    FileResponse fileVoToFileResponse(FileVo fileVo);

}
