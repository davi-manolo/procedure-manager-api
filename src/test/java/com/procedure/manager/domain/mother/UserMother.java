package com.procedure.manager.domain.mother;

import com.procedure.manager.domain.model.UserModel;
import com.procedure.manager.domain.vo.UserVo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMother {

    public static UserVo getUserVo() {
        UserVo userVo = new UserVo();
        userVo.setUserId(1L);
        userVo.setName("Fulano");
        userVo.setSurName("Ciclano");
        userVo.setEmail("fulano.ciclano@email.com");
        userVo.setPassword("123abc");
        return userVo;
    }

    public static UserModel getUserModel() {
        UserModel userModel = new UserModel();
        userModel.setUserId(1L);
        userModel.setName("Fulano");
        userModel.setSurName("Ciclano");
        userModel.setEmail("fulano.ciclano@email.com");
        userModel.setPassword("123abc");
        return userModel;
    }

    public static Optional<UserModel> getUserModelOptional() {
        return Optional.of(getUserModel());
    }

    public static Optional<UserModel> getEmptyUserModelOptional() {
        return Optional.empty();
    }

}
