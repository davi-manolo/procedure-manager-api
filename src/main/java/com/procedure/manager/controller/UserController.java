package com.procedure.manager.controller;

import com.procedure.manager.domain.mapper.UserMapper;
import com.procedure.manager.domain.request.UserRequest;
import com.procedure.manager.domain.response.UserResponse;
import com.procedure.manager.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
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
@Api(tags = "Usuário")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/user/register")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Registrar um novo usuário.")
    @ApiResponse(code = 200, message = "Realiza o registro de um novo usuário no banco de dados.")
    public void registerUser(
            @Valid @RequestBody
            @ApiParam(value = "Objeto para registrar um novo usuário.")
            UserRequest userRequest
    ) {
        userService.registerUser(userMapper.requestToVo(userRequest));
    }

    @GetMapping("/user/get")
    @ResponseStatus(HttpStatus.OK)@ApiOperation("Buscar o usuário registrado.")
    @ApiResponse(code = 200, message = "Retorna o usuário registrado no banco de dados.")
    public UserResponse getUser(
            @NotBlank(message = "{validation.email.notBlank}")
            @Email(message = "{validation.email.notValid}")
            @ApiParam(value = "Email para buscar as informações do usuário.")
            @RequestParam("userMail") String userMail
    ) {
        return userMapper.voToResponse(userService.getUser(userMail));
    }

}
