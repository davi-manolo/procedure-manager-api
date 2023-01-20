package com.procedure.manager.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataToCreateProcedureTypeRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("percentage")
    private Double percentage;

    @JsonProperty("userId")
    private Long userId;

}
