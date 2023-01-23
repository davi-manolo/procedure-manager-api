package com.procedure.manager.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Objeto de requisição para criar um tipo de procedimento.")
public class DataToCreateProcedureTypeRequest {

    @NotBlank(message = "{validation.procedure.type.name.notBlank}")
    @JsonProperty("name")
    @ApiModelProperty("Nome do tipo de procedimento.")
    private String name;

    @NotNull(message = "{validation.procedure.type.percentage.notNull}")
    @DecimalMin(value = "1.0", message = "{validation.procedure.type.percentage.min}")
    @JsonProperty("percentage")
    @ApiModelProperty("Porcentagem de lucro que irá receber encima do valor total.")
    private Double percentage;

    @NotNull(message = "{validation.procedure.type.user.notNull}")
    @JsonProperty("userId")
    @ApiModelProperty("ID do usuário (profissional) que criou o tipo de procedimento.")
    private Long userId;

}
