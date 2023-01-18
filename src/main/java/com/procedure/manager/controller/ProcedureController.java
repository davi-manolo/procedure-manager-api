package com.procedure.manager.controller;

import com.procedure.manager.domain.mapper.ProcedureMapper;
import com.procedure.manager.domain.request.DataToCreateProcedureRequest;
import com.procedure.manager.domain.response.ProcedureResponse;
import com.procedure.manager.domain.vo.DataSearchProcedureMonthVo;
import com.procedure.manager.service.ProcedureService;
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
public class ProcedureController {

    @Autowired
    private ProcedureService procedureService;

    @Autowired
    private ProcedureMapper procedureMapper;

    @PostMapping("/procedure/register")
    @ResponseStatus(HttpStatus.OK)
    public void registerProcedure(@RequestBody DataToCreateProcedureRequest dataToCreateProcedureRequest) {
        procedureService.registerProcedure(procedureMapper.requestToVo(dataToCreateProcedureRequest));
    }

    @GetMapping("/procedure/get-period")
    @ResponseStatus(HttpStatus.OK)
    public List<ProcedureResponse> getProcedure(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @RequestParam("userId") Long userId
    ) {
        return procedureMapper.voToResponseList(procedureService.getProcedureListByPeriod(
                new DataSearchProcedureMonthVo(month, year, userId))
        );
    }

    @PatchMapping("/procedure/edit")
    @ResponseStatus(HttpStatus.OK)
    public void editProcedure(@RequestParam("procedureId") Long procedureId,
                              @RequestBody DataToCreateProcedureRequest dataToCreateProcedureRequest
    ) {
        procedureService.editProcedure(procedureId, procedureMapper.requestToVo(dataToCreateProcedureRequest));
    }

    @PatchMapping("/procedure/disable")
    @ResponseStatus(HttpStatus.OK)
    public void disableProcedure(@RequestParam Long procedureId) {
        procedureService.disableProcedure(procedureId);
    }

}
