package com.procedure.manager.service;

import com.procedure.manager.domain.exception.DatabaseException;
import com.procedure.manager.domain.mapper.UserMapper;
import com.procedure.manager.domain.model.UserModel;
import com.procedure.manager.domain.vo.UserVo;
import com.procedure.manager.repository.UserRepository;
import com.procedure.manager.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static com.procedure.manager.domain.enumeration.ExceptionMessage.DATABASE_USER_ALREADY_EXISTS;
import static com.procedure.manager.domain.enumeration.ExceptionMessage.DATABASE_USER_DOES_NOT_EXIST;
import static com.procedure.manager.domain.mother.UserMother.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class UserServiceUnitTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private String email;
    private Long id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.email = "davi.manolo@gmail.com";
        this.id = 1L;
    }

    @Test
    void givenUserVoWhenCallRegisterUserThenSaveUser() {

        UserVo userVo = getUserVo();
        UserModel userModel = getUserModel();

        when(userRepository.save(any())).thenReturn(userModel);
        when(userMapper.voToModel(any())).thenReturn(userModel);

        userService.registerUser(userVo);

        assertEquals("Fulano", userVo.getName());
        assertEquals("Ciclano", userVo.getSurName());
        assertEquals("fulano.ciclano@email.com", userModel.getEmail());
        assertEquals("123abc", userModel.getPassword());
        assertEquals(1L, userModel.getUserId());

        verify(userMapper).voToModel(userVo);
        verify(userRepository).save(userModel);

    }

    @Test
    void givenUserVoWhenCallRegisterUserThenThrowDatabaseException() {

        UserVo userVo = getUserVo();
        Optional<UserModel> optionalUserModel = getUserModelOptional();

        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(optionalUserModel);

        DatabaseException databaseException = assertThrows(DatabaseException.class, () -> userService.registerUser(userVo));

        assertEquals(databaseException.getMessage(), DATABASE_USER_ALREADY_EXISTS.getMessageKey());

        verify(userRepository).findByEmailIgnoreCase(any());

    }

    @Test
    void givenEmailWhenCallGetUserByEmailThenReturnUser() {

        UserModel userModel = getUserModel();
        Optional<UserModel> optionalUserModel = getUserModelOptional();
        UserVo userVo = getUserVo();

        when(userRepository.findByEmailIgnoreCase(email)).thenReturn(optionalUserModel);
        when(userMapper.modelToVo(optionalUserModel.get())).thenReturn(userVo);

        UserVo result = userService.getUser(email);

        assertEquals(result.getName(), userModel.getName());
        assertEquals(result.getSurName(), userModel.getSurName());
        assertEquals(result.getEmail(), userModel.getEmail());
        assertEquals(result.getPassword(), userModel.getPassword());
        assertEquals(result.getUserId(), userModel.getUserId());

        verify(userMapper).modelToVo(optionalUserModel.get());

    }

    @Test
    void givenIdWhenCallGetUserByIdThenReturnUser() {

        UserModel userModel = getUserModel();
        Optional<UserModel> optionalUserModel = getUserModelOptional();
        UserVo userVo = getUserVo();

        when(userRepository.findById(id)).thenReturn(optionalUserModel);
        when(userMapper.modelToVo(optionalUserModel.get())).thenReturn(userVo);

        UserVo result = userService.getUser(id);

        assertEquals(result.getName(), userModel.getName());
        assertEquals(result.getSurName(), userModel.getSurName());
        assertEquals(result.getEmail(), userModel.getEmail());
        assertEquals(result.getPassword(), userModel.getPassword());
        assertEquals(result.getUserId(), userModel.getUserId());

        verify(userMapper).modelToVo(optionalUserModel.get());

    }

    @Test
    void givenEmailWhenCallGetUserByEmailThenThrowDatabaseException() {

        Optional<UserModel> optionalEmptyUserModel = getEmptyUserModelOptional();

        when(userRepository.findByEmailIgnoreCase(email)).thenReturn(optionalEmptyUserModel);

        DatabaseException databaseException = assertThrows(DatabaseException.class, () -> userService.getUser(email));

        assertEquals(databaseException.getMessage(), DATABASE_USER_DOES_NOT_EXIST.getMessageKey());

        verify(userRepository).findByEmailIgnoreCase(any());

    }

    @Test
    void givenIdWhenCallGetUserByIdThenThrowDatabaseException() {

        Optional<UserModel> optionalEmptyUserModel = getEmptyUserModelOptional();

        when(userRepository.findById(id)).thenReturn(optionalEmptyUserModel);

        DatabaseException databaseException = assertThrows(DatabaseException.class, () -> userService.getUser(id));

        assertEquals(databaseException.getMessage(), DATABASE_USER_DOES_NOT_EXIST.getMessageKey());

        verify(userRepository).findById(any());

    }

    @Test
    void givenEmailWhenCallLoadUserByUsernameThenReturnUserDetails() {

        Optional<UserModel> optionalUserModel = getUserModelOptional();
        UserDetails userDetails = getUserDetails();
        UserVo userVo = getUserVo();

        when(userRepository.findByEmailIgnoreCase(email)).thenReturn(optionalUserModel);
        when(userMapper.modelToVo(optionalUserModel.get())).thenReturn(userVo);
        when(userMapper.userVoToUser(userVo)).thenReturn((User) userDetails);

        UserDetails result = userService.loadUserByUsername(email);

        assertEquals(result.getUsername(), userVo.getEmail());
        assertEquals(result.getPassword(), userVo.getPassword());

        verify(userRepository).findByEmailIgnoreCase(any());
        verify(userMapper).modelToVo(any());
        verify(userMapper).userVoToUser(any());

    }

}
