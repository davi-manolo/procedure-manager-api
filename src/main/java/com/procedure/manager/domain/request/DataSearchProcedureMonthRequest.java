package com.procedure.manager.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSearchProcedureMonthRequest {

    @JsonProperty("month")
    @Positive(message = "{validation.share.month.positive}")
    @NotNull(message = "{validation.share.month.notNull}")
    private Integer month;

    @JsonProperty("year")
    @Positive(message = "{validation.share.year.positive}")
    @NotNull(message = "{validation.share.year.notNull}")
    private Integer year;

    @JsonProperty("userId")
    @Positive(message = "{validation.share.user.positive}")
    @NotNull(message = "{validation.share.user.notNull}")
    private Long userId;

}
