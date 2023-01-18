package com.procedure.manager.controller;

import com.procedure.manager.domain.mapper.UserMapper;
import com.procedure.manager.domain.request.UserRequest;
import com.procedure.manager.domain.response.UserResponse;
import com.procedure.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Validated
@RestController
@RequestMapping("/v1")
@SuppressWarnings("unused")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/user/register")
    @ResponseStatus(HttpStatus.OK)
    public void registerUser(@Valid @RequestBody UserRequest userRequest) {
        userService.registerUser(userMapper.requestToVo(userRequest));
    }

    @GetMapping("/user/get")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse registerUser(
            @NotBlank(message = "{validation.email.notBlank}")
            @Email(message = "{validation.email.notValid}")
            @RequestParam("userMail") String userMail) {
        return userMapper.voToResponse(userService.getUser(userMail));
    }

}
