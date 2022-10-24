package com.procedure.manager.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "{validation.user.name.notBlank}")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "{validation.user.surname.notBlank}")
    @JsonProperty("surName")
    private String surName;

    @NotBlank(message = "{validation.email.notBlank}")
    @Email(message = "{validation.email.notValid}")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "{validation.password.notBlank}")
    @Length(min = 8, max = 16, message = "{validation.password.notValid}")
    private String password;

}
