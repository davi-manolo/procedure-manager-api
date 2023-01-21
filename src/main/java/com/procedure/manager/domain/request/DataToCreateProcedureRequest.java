package com.procedure.manager.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataToCreateProcedureRequest {

    @JsonProperty("procedureDate")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "{validation.procedure.date.notNull}")
    @PastOrPresent(message = "{validation.procedure.date.pastOrPresent}")
    private LocalDate procedureDate;

    @JsonProperty("customer")
    @NotEmpty(message = "{validation.procedure.customer.notEmpty}")
    private String customer;

    @JsonProperty("value")
    @NotNull(message = "{validation.procedure.value.notNull}")
    private BigDecimal value;

    @JsonProperty("procedureTypeId")
    @NotNull(message = "{validation.procedure.type.id.notNull}")
    private Long procedureTypeId;

    @JsonProperty("userId")
    @NotNull(message = "{validation.procedure.user.notNull}")
    private Long userId;

}
