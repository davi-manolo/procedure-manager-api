package com.procedure.manager.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Objeto de requisição para compartilhar arquivo.")
public class DataSearchProcedureMonthRequest {

    @JsonProperty("month")
    @Positive(message = "{validation.share.month.positive}")
    @NotNull(message = "{validation.share.month.notNull}")
    @ApiModelProperty("Mês para buscar procedimentos no formato de 1 até 12.")
    private Integer month;

    @JsonProperty("year")
    @Positive(message = "{validation.share.year.positive}")
    @NotNull(message = "{validation.share.year.notNull}")
    @ApiModelProperty("Ano para buscar procedimentos.")
    private Integer year;

    @JsonProperty("userId")
    @Positive(message = "{validation.share.user.positive}")
    @NotNull(message = "{validation.share.user.notNull}")
    @ApiModelProperty("ID do usuário (profissional) que realizou os procedimentos.")
    private Long userId;

}
