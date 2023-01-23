package com.procedure.manager.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("Objeto de resposta do usuário.")
public class UserResponse {

    @JsonProperty("userId")
    @ApiModelProperty("ID do usuário (profissional) registrado.")
    private Long userId;

    @JsonProperty("name")
    @ApiModelProperty("Nome do usuário (profissional) registrado.")
    private String name;

    @JsonProperty("surName")
    @ApiModelProperty("Sobrenome do usuário (profissional) registrado.")
    private String surName;

}
