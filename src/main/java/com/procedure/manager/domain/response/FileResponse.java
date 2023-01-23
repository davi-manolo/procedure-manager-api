package com.procedure.manager.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("Objeto de resposta do arquivo compartilhado.")
public class FileResponse {

    @JsonProperty("title")
    @ApiModelProperty("Nome do arquivo seguido com dia, mês, ano, hora, minuto e segundo concatenados.")
    private String title;

    @JsonProperty("content")
    @ApiModelProperty("Arquivo criptografado com Base 64 para transferência entre microserviços.")
    private String contentBase64;

}
