package com.procedure.manager.service;

import com.procedure.manager.domain.vo.UserVo;

public interface UserService {

    void registerUser(UserVo userVo);

    UserVo getUser(String userMail);

    UserVo getUser(Long userId);

}
