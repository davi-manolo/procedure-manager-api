package com.procedure.manager.controller;

import com.procedure.manager.domain.mapper.ProcedureTypeMapper;
import com.procedure.manager.domain.request.DataToCreateProcedureTypeRequest;
import com.procedure.manager.domain.response.ProcedureTypeResponse;
import com.procedure.manager.service.ProcedureTypeService;
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
import java.util.List;

@Validated
@RestController
@RequestMapping("/v1")
@SuppressWarnings("unused")
@Api(tags = "Tipos de Procedimento")
public class ProcedureTypeController {

    @Autowired
    private ProcedureTypeMapper procedureTypeMapper;

    @Autowired
    private ProcedureTypeService procedureTypeService;

    @PostMapping("/procedure-type/register")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Registrar um novo tipo de procedimento.")
    @ApiResponse(code = 200, message = "Realiza o registro de um novo tipo de procedimento no banco de dados.")
    public void registerProcedureType(
            @Valid @RequestBody
            @ApiParam(value = "Objeto para registrar um novo tipo de procedimento.")
            DataToCreateProcedureTypeRequest dataToCreateProcedureTypeRequest
    ) {
        procedureTypeService.registerProcedureType(procedureTypeMapper.requestToVo(dataToCreateProcedureTypeRequest));
    }

    @GetMapping("/procedure-type/get")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Buscar um tipo de procedimento.")
    @ApiResponse(code = 200, message = "Retorna um tipo de procedimentos de acordo com o ID do usuário.")
    public ProcedureTypeResponse getProcedureType(
            @RequestParam
            @ApiParam(value = "ID do tipo de procedimento para buscar-lo.")
            Long procedureTypeId
    ) {
        return procedureTypeMapper.voToResponse(procedureTypeService.getProcedureType(procedureTypeId));
    }

    @GetMapping("/procedure-type/get-all")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Busca lista de tipos de procedimento.")
    @ApiResponse(code = 200, message = "Retorna a lista de tipos de procedimento de acordo com o ID do usuário.")
    public List<ProcedureTypeResponse> getProcedureTypeListByUser(
            @RequestParam("userId")
            @ApiParam(value = "ID do usuário (profissional) para buscar todos os tipo de procedimentos.")
            Long userId
    ) {
        return procedureTypeMapper.voListToResponseList(procedureTypeService.getProcedureTypeListByUser(userId));
    }

    @PatchMapping("/procedure-type/edit")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Editar um tipo de procedimento.")
    @ApiResponse(code = 200, message = "Edita um tipo de procedimento já existente e atualiza no banco de dados.")
    public void editProcedureType(

            @RequestParam("procedureTypeId")
            @ApiParam(value = "ID do tipo de procedimento para atualizar.")
            Long procedureTypeId,

            @Valid @RequestBody
            @ApiParam(value = "Objeto para atualizar o tipo de procedimento.")
            DataToCreateProcedureTypeRequest dataToCreateProcedureTypeRequest

    ) {
        procedureTypeService.editProcedureType(procedureTypeId, procedureTypeMapper.requestToVo(dataToCreateProcedureTypeRequest));
    }

    @PatchMapping("/procedure-type/disable")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Desabilitar um tipo de procedimento.")
    @ApiResponse(code = 200, message = "Desabilita um tipo de procedimento já existente (tipos procedimentos não são excluídos de fato).")
    public void disableProcedureType(
            @RequestParam
            @ApiParam(value = "ID do tipo de procedimento para desabilitar.")
            Long procedureTypeId
    ) {
        procedureTypeService.disableProcedureType(procedureTypeId);
    }

}
