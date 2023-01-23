package com.procedure.manager.controller;

import com.procedure.manager.domain.enumeration.Extension;
import com.procedure.manager.domain.mapper.ShareMapper;
import com.procedure.manager.domain.request.DataSearchProcedureMonthRequest;
import com.procedure.manager.domain.response.FileResponse;
import com.procedure.manager.service.FileGeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
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
@Api(tags = "Compartilhar")
public class ShareController {

    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private FileGeneratorService fileGeneratorService;

    @PostMapping("/share")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Compartilha arquivo.")
    @ApiResponse(code = 200, message = "Retorna um arquivo em formato base64 criptografado.")
    public FileResponse share(

            @NotNull(message = "{validation.share.extension.notNull}")
            @RequestParam("extension")
            @ApiParam(value = "Extensão do arquivo como XLS ou PDF para gerar o arquivo.")
            Extension extension,

            @Valid @RequestBody
            @ApiParam(value = "Objeto para realizar a busca de informações para gerar o arquivo.")
            DataSearchProcedureMonthRequest dataSearchProcedureMonthRequest
    ) {
        return shareMapper.fileVoToFileResponse(
                fileGeneratorService.generateFile(shareMapper.requestToVo(dataSearchProcedureMonthRequest), extension)
        );
    }

}
