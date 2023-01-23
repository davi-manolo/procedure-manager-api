package com.procedure.manager.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("Objeto de resposta do tipo de procedimento.")
public class ProcedureTypeResponse {

    @JsonProperty("procedureTypeId")
    @ApiModelProperty("ID do tipo de procedimento registrado.")
    private Long procedureTypeId;

    @JsonProperty("name")
    @ApiModelProperty("Nome do tipo de procedimento registrado.")
    private String name;

    @JsonProperty("percentage")
    @ApiModelProperty("Porcentagem de lucro para calcular o valor a receber.")
    private Double percentage;

}
