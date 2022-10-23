package com.procedure.manager.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcedureResponse {

    @JsonProperty("procedureId")
    private Long procedureId;

    @JsonProperty("procedureDate")
    private LocalDate procedureDate;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("procedureTypeName")
    private String procedureTypeName;

    @JsonProperty("procedureValue")
    private BigDecimal procedureValue;

    @JsonProperty("receivedValue")
    private BigDecimal receivedValue;

}
