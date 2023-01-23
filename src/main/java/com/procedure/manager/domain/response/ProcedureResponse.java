package com.procedure.manager.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("Objeto de resposta de procedimento.")
public class ProcedureResponse {

    @JsonProperty("procedureId")
    @ApiModelProperty("ID do procedimento registrado.")
    private Long procedureId;

    @JsonProperty("procedureDate")
    @ApiModelProperty("Data do procedimento registrado.")
    private LocalDate procedureDate;

    @JsonProperty("customer")
    @ApiModelProperty("Nome do cliente registrado para esse procedimento.")
    private String customer;

    @JsonProperty("procedureTypeName")
    @ApiModelProperty("Nome do tipo de procedimento para esse procedimento.")
    private String procedureTypeName;

    @JsonProperty("procedureValue")
    @ApiModelProperty("Valor total do procedimento.")
    private BigDecimal procedureValue;

    @JsonProperty("valueReceived")
    @ApiModelProperty("Valor da comissão recebida desse procedimento.")
    private BigDecimal valueReceived;

}
