package com.procedure.manager.controller;

import com.procedure.manager.domain.mapper.ProcedureMapper;
import com.procedure.manager.domain.request.DataToCreateProcedureRequest;
import com.procedure.manager.domain.response.ProcedureResponse;
import com.procedure.manager.domain.vo.DataSearchProcedureMonthVo;
import com.procedure.manager.service.ProcedureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Validated
@RestController
@RequestMapping("/v1")
@Api(tags = "Procedimentos")
@SuppressWarnings("unused")
public class ProcedureController {

    @Autowired
    private ProcedureService procedureService;

    @Autowired
    private ProcedureMapper procedureMapper;

    @PostMapping("/procedure/register")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Registrar um novo procedimento.")
    @ApiResponse(code = 200, message = "Realiza o registro de um novo procedimento no banco de dados.")
    public void registerProcedure(
            @Valid @RequestBody
            @ApiParam(value = "Objeto de entrada para cadastrar novo procedimento.")
            DataToCreateProcedureRequest dataToCreateProcedureRequest) {
        procedureService.registerProcedure(procedureMapper.requestToVo(dataToCreateProcedureRequest));
    }

    @GetMapping("/procedure/get-period")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Buscar procedimento por período.")
    @ApiResponse(code = 200, message = "Retorna a lista de procedimentos de acordo com o período e usuário.")
    public List<ProcedureResponse> getProcedure(

            @Positive(message = "{validation.procedure.month.positive}")
            @ApiParam(value = "Mês de 1 até 12 para buscar procedimento.")
            @RequestParam("month") int month,

            @Positive(message = "{validation.procedure.year.positive}")
            @ApiParam(value = "Ano para buscar procedimento.")
            @RequestParam("year") int year,

            @Positive(message = "{validation.procedure.user.positive}")
            @ApiParam(value = "ID do usuário para buscar procedimento.")
            @RequestParam("userId") Long userId
    ) {
        return procedureMapper.voToResponseList(procedureService.getProcedureListByPeriod(
                new DataSearchProcedureMonthVo(month, year, userId))
        );
    }

    @PatchMapping("/procedure/edit")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Editar um procedimento.")
    @ApiResponse(code = 200, message = "Edita um procedimento já existente e atualiza no banco de dados.")
    public void editProcedure(

            @Positive(message = "{validation.procedure.type.id.positive}")
            @ApiParam(value = "ID do procedimento para atualizar-lo.")
            @RequestParam("procedureId") Long procedureId,

            @Valid @RequestBody
            @ApiParam(value = "Objeto para atualizar o procedimento já existente.")
            DataToCreateProcedureRequest dataToCreateProcedureRequest
    ) {
        procedureService.editProcedure(procedureId, procedureMapper.requestToVo(dataToCreateProcedureRequest));
    }

    @PatchMapping("/procedure/disable")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Desabilitar um procedimento.")
    @ApiResponse(code = 200, message = "Desabilita um procedimento já existente (procedimentos não são excluídos de fato).")
    public void disableProcedure(
            @Positive(message = "{validation.procedure.type.id.positive}")
            @ApiParam(value = "ID do procedimento para desativar.")
            @RequestParam Long procedureId
    ) {
        procedureService.disableProcedure(procedureId);
    }

}
