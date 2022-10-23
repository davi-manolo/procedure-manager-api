package com.procedure.manager.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("surName")
    private String surName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

}
