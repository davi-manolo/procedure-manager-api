package com.procedure.manager.domain.mapper;

import com.procedure.manager.domain.model.UserModel;
import com.procedure.manager.domain.request.UserRequest;
import com.procedure.manager.domain.response.UserResponse;
import com.procedure.manager.domain.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserVo requestToVo(UserRequest userRequest);

    UserModel voToModel(UserVo userVo);

    UserVo modelToVo(UserModel userModel);

    UserResponse voToResponse(UserVo userVo);

}
