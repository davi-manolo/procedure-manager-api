package com.procedure.manager.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {

    private Long userId;
    private String name;
    private String surName;
    private String email;
    private String password;

}
