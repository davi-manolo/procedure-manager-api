package com.procedure.manager.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSearchProcedureMonthRequest {

    @JsonProperty("month")
    private Integer month;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("userId")
    private Long userId;

}
