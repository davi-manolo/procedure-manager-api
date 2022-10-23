package com.procedure.manager.domain.exception.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ApiModel("ExceptionResponse")
public class ExceptionResponse {

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm:ss")
    @ApiModelProperty(name = "timestamp", value = "Data/Hora")
    private LocalDateTime timestamp;

    @ApiModelProperty(name = "message", value = "Mensagem do erro")
    private String message;

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @ApiModelProperty(name = "errorList", value = "Lista de erros")
    private List<ObjectError> errorList;

}