package com.procedure.manager.controller;

import com.procedure.manager.domain.mapper.ProcedureTypeMapper;
import com.procedure.manager.domain.request.ProcedureTypeRequest;
import com.procedure.manager.domain.response.ProcedureTypeResponse;
import com.procedure.manager.service.ProcedureTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("unused")
public class ProcedureTypeController {

    @Autowired
    private ProcedureTypeMapper procedureTypeMapper;

    @Autowired
    private ProcedureTypeService procedureTypeService;

    @PostMapping("/procedure-type/register")
    @ResponseStatus(HttpStatus.OK)
    public void registerProcedureType(@RequestBody ProcedureTypeRequest procedureTypeRequest) {
        procedureTypeService.registerProcedureType(procedureTypeMapper.requestToVo(procedureTypeRequest));
    }

    @GetMapping("/procedure-type/get")
    @ResponseStatus(HttpStatus.OK)
    public ProcedureTypeResponse getProcedureType(@RequestParam Long procedureId) {
        return procedureTypeMapper.voToResponse(procedureTypeService.getProcedureType(procedureId));
    }

    @GetMapping("/procedure-type/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProcedureTypeResponse> getProcedureTypeList() {
        return procedureTypeMapper.voListToResponseList(procedureTypeService.getProcedureTypeList());
    }

    @PatchMapping("/procedure-type/edit")
    @ResponseStatus(HttpStatus.OK)
    public void editProcedureType(@RequestBody ProcedureTypeRequest procedureTypeRequest) {
        procedureTypeService.editProcedureType(procedureTypeMapper.requestToVo(procedureTypeRequest));
    }

    @PatchMapping("/procedure-type/disable")
    @ResponseStatus(HttpStatus.OK)
    public void disableProcedureType(@RequestParam Long procedureTypeId) {
        procedureTypeService.disableProcedureType(procedureTypeId);
    }

}
