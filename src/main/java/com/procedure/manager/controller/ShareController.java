package com.procedure.manager.controller;

import com.procedure.manager.domain.enumeration.Extension;
import com.procedure.manager.domain.mapper.ShareMapper;
import com.procedure.manager.domain.request.DataSearchProcedureMonthRequest;
import com.procedure.manager.service.FileGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class ShareController {

    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private FileGeneratorService fileGeneratorService;

    @PostMapping("/share")
    @ResponseStatus(HttpStatus.OK)
    public String share(
            @RequestParam("extension") Extension extension,
            @RequestBody DataSearchProcedureMonthRequest dataSearchProcedureMonthRequest
    ) {
        return fileGeneratorService.generateFile(shareMapper.requestToVo(dataSearchProcedureMonthRequest), extension);
    }

}
