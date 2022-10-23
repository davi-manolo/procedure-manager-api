package com.procedure.manager.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataContentProcedureRequest {

    @JsonProperty("procedureDate")
    private String procedureDate;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("procedureTypeName")
    private String procedureTypeName;

    @JsonProperty("procedureValue")
    private String procedureValue;

    @JsonProperty("receivedValue")
    private String receivedValue;

}
