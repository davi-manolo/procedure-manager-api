package com.procedure.manager.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcedureTypeResponse {

    @JsonProperty("procedureTypeId")
    private Long procedureTypeId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("percentage")
    private Double percentage;

}
