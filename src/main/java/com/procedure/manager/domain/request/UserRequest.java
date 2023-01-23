package com.procedure.manager.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Objeto de requisição para criar usuário.")
public class UserRequest {

    @NotBlank(message = "{validation.user.name.notBlank}")
    @JsonProperty("name")
    @ApiModelProperty("Primeiro nome do usuário (profissional).")
    private String name;

    @NotBlank(message = "{validation.user.surname.notBlank}")
    @JsonProperty("surName")
    @ApiModelProperty("Sobrenome do usuário (profissional).")
    private String surName;

    @NotBlank(message = "{validation.email.notBlank}")
    @Email(message = "{validation.email.notValid}")
    @JsonProperty("email")
    @ApiModelProperty("Email do usuário (profissional) para cadastro.")
    private String email;

    @NotBlank(message = "{validation.password.notBlank}")
    @Length(min = 8, message = "{validation.password.notValid}")
    @ApiModelProperty("Senha do usuário (profissional) para cadastrar. Deve conter pelo menos 8 caracteres.")
    private String password;

}
