package com.procedure.manager.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataToCreateProcedureTypeRequest {

    @NotBlank(message = "{validation.procedure.type.name.notBlank}")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "{validation.procedure.type.percentage.notNull}")
    @DecimalMin(value = "1.0", message = "{validation.procedure.type.percentage.min}")
    @JsonProperty("percentage")
    private Double percentage;

    @NotNull(message = "{validation.procedure.type.user.notNull}")
    @JsonProperty("userId")
    private Long userId;

}
