package com.procedure.manager.domain.mapper;

import com.procedure.manager.domain.vo.DataContentForFileVo;
import com.procedure.manager.domain.vo.ProcedureVo;
import com.procedure.manager.util.FormatUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { ProcedureMapper.class }, imports = {FormatUtils.class})
public interface FileGeneratorMapper {

    @Mapping(target = "totalReceived", expression = "java(FormatUtils.convertToMonetaryValue(totalReceived))")
    @Mapping(target = "month", expression = "java(FormatUtils.convertNumberToMonth(month))")
    @Mapping(source = "procedureVoList", target = "procedureList")
    DataContentForFileVo toDataContentForFileVo(Integer month, BigDecimal totalReceived, List<ProcedureVo> procedureVoList);

}
