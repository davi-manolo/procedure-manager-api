package com.procedure.manager.controller;

import com.procedure.manager.domain.enumeration.Extension;
import com.procedure.manager.domain.mapper.ShareMapper;
import com.procedure.manager.domain.request.DataSearchProcedureMonthRequest;
import com.procedure.manager.domain.response.FileResponse;
import com.procedure.manager.service.FileGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/v1")
@SuppressWarnings("unused")
public class ShareController {

    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private FileGeneratorService fileGeneratorService;

    @PostMapping("/share")
    @ResponseStatus(HttpStatus.OK)
    public FileResponse share(
            @NotNull(message = "{validation.share.extension.notNull}") @RequestParam("extension") Extension extension,
            @Valid @RequestBody DataSearchProcedureMonthRequest dataSearchProcedureMonthRequest
    ) {
        return shareMapper.fileVoToFileResponse(
                fileGeneratorService.generateFile(shareMapper.requestToVo(dataSearchProcedureMonthRequest), extension)
        );
    }

}
