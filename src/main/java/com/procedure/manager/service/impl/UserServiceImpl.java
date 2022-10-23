package com.procedure.manager.service.impl;

import com.procedure.manager.domain.exception.DatabaseException;
import com.procedure.manager.domain.mapper.UserMapper;
import com.procedure.manager.domain.model.UserModel;
import com.procedure.manager.domain.vo.UserVo;
import com.procedure.manager.repository.UserRepository;
import com.procedure.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.procedure.manager.domain.enumeration.ExceptionMessage.DATABASE_USER_ALREADY_EXISTS;
import static com.procedure.manager.domain.enumeration.ExceptionMessage.DATABASE_USER_DOES_NOT_EXIST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void registerUser(UserVo userVo) {
        Optional<UserModel> optional = userRepository.findByEmailIgnoreCase(userVo.getEmail());
        if(optional.isPresent()) {
            throw new DatabaseException(CONFLICT, DATABASE_USER_ALREADY_EXISTS);
        }
        userRepository.save(userMapper.voToModel(userVo));
    }

    @Override
    public UserVo getUser(String userMail) {
        Optional<UserModel> optional = userRepository.findByEmailIgnoreCase(userMail);
        if(optional.isEmpty()) {
            throw new DatabaseException(NOT_FOUND, DATABASE_USER_DOES_NOT_EXIST);
        }
        return userMapper.modelToVo(optional.get());
    }

    @Override
    public UserVo getUser(Long userId) {
        Optional<UserModel> optional = userRepository.findById(userId);
        if(optional.isEmpty()) {
            throw new DatabaseException(NOT_FOUND, DATABASE_USER_DOES_NOT_EXIST);
        }
        return userMapper.modelToVo(optional.get());
    }

}