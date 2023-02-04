package com.procedure.manager.domain.mapper;

import com.procedure.manager.domain.model.UserModel;
import com.procedure.manager.domain.request.UserRequest;
import com.procedure.manager.domain.response.UserResponse;
import com.procedure.manager.domain.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserVo requestToVo(UserRequest userRequest);

    UserModel voToModel(UserVo userVo);

    UserVo modelToVo(UserModel userModel);

    UserResponse voToResponse(UserVo userVo);

    default User userVoToUser(UserVo userVo) {
        return new User(userVo.getEmail(), userVo.getPassword(), Collections.emptyList());
    }

}
