package com.procedure.manager.service.impl;

import com.procedure.manager.domain.exception.DatabaseException;
import com.procedure.manager.domain.mapper.UserMapper;
import com.procedure.manager.domain.model.UserModel;
import com.procedure.manager.domain.vo.UserVo;
import com.procedure.manager.repository.UserRepository;
import com.procedure.manager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.procedure.manager.domain.enumeration.ExceptionMessage.DATABASE_USER_ALREADY_EXISTS;
import static com.procedure.manager.domain.enumeration.ExceptionMessage.DATABASE_USER_DOES_NOT_EXIST;
import static com.procedure.manager.util.DataEncoderUtils.encodePassword;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
@SuppressWarnings("unused")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void registerUser(UserVo userVo) {
        Optional<UserModel> optional = userRepository.findByEmailIgnoreCase(userVo.getEmail());
        if(optional.isPresent()) {
            log.error("Já existe um usuário com o email {}.", userVo.getEmail());
            throw new DatabaseException(CONFLICT, DATABASE_USER_ALREADY_EXISTS);
        }
        userVo.setPassword(encodePassword(userVo.getPassword()));
        userRepository.save(userMapper.voToModel(userVo));
        log.info("Usuário cadastrado com o nome {} {} e email {}.", userVo.getName(), userVo.getSurName(), userVo.getEmail());
    }

    @Override
    public UserVo getUser(String userMail) {
        Optional<UserModel> optional = userRepository.findByEmailIgnoreCase(userMail);
        if(optional.isEmpty()) {
            log.error("Não existe usuário com email {}.", userMail);
            throw new DatabaseException(NOT_FOUND, DATABASE_USER_DOES_NOT_EXIST);
        }
        log.info("Usuário resgatado do banco de dados: {}", optional.get());
        return userMapper.modelToVo(optional.get());
    }

    @Override
    public UserVo getUser(Long userId) {
        Optional<UserModel> optional = userRepository.findById(userId);
        if(optional.isEmpty()) {
            log.error("Não existe usuário com ID {}.", userId);
            throw new DatabaseException(NOT_FOUND, DATABASE_USER_DOES_NOT_EXIST);
        }
        log.info("Usuário resgatado do banco de dados: {}", optional.get());
        return userMapper.modelToVo(optional.get());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userMapper.userVoToUser(getUser(email));
    }

}
