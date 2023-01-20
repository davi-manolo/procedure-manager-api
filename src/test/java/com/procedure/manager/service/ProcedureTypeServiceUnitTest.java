package com.procedure.manager.service;

import com.procedure.manager.domain.exception.DatabaseException;
import com.procedure.manager.domain.mapper.ProcedureTypeMapper;
import com.procedure.manager.domain.model.ProcedureTypeModel;
import com.procedure.manager.domain.vo.ProcedureTypeCreationDataVo;
import com.procedure.manager.domain.vo.ProcedureTypeVo;
import com.procedure.manager.domain.vo.UserVo;
import com.procedure.manager.repository.ProcedureTypeRepository;
import com.procedure.manager.service.impl.ProcedureTypeServiceImpl;
import com.procedure.manager.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.procedure.manager.domain.enumeration.ExceptionMessage.DATABASE_PROCEDURE_TYPE_ALREADY_EXISTS;
import static com.procedure.manager.domain.enumeration.ExceptionMessage.DATABASE_PROCEDURE_TYPE_DOES_NOT_EXIST;
import static com.procedure.manager.domain.enumeration.ExceptionMessage.DATABASE_PROCEDURE_TYPE_LIST_DOES_NOT_EXIST;
import static com.procedure.manager.domain.mother.ProcedureTypeMother.*;
import static com.procedure.manager.domain.mother.UserMother.getUserVo;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class ProcedureTypeServiceUnitTest {

    @InjectMocks
    private ProcedureTypeServiceImpl procedureTypeService;

    @Mock
    private ProcedureTypeRepository procedureTypeRepository;

    @Mock
    private ProcedureTypeMapper procedureTypeMapper;

    @Mock
    private UserServiceImpl userService;

    private Long id;
    private String name;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.id = 1L;
        this.name = "Tipo de Procedimento";
    }

    @Test
    void givenProcedureTypeVoWhenCallRegisterProcedureTypeThenSaveProcedureType() {

        ProcedureTypeCreationDataVo procedureTypeCreationDataVo = getProcedureTypeCreationDataVo();
        Optional<ProcedureTypeModel> optionalProcedureTypeModel = getEmptyProcedureTypeModelOptional();
        ProcedureTypeVo procedureTypeVo = getProcedureTypeWithIdNullVo();
        ProcedureTypeModel procedureTypeModel = getProcedureTypeWithIdNullModel();
        UserVo userVo = getUserVo();

        when(procedureTypeRepository.findByUser_userIdAndNameIgnoreCase(
                procedureTypeCreationDataVo.getUserId(), procedureTypeCreationDataVo.getName())
        ).thenReturn(optionalProcedureTypeModel);
        when(userService.getUser(procedureTypeCreationDataVo.getUserId())).thenReturn(userVo);
        when(procedureTypeMapper.voToModel(procedureTypeVo)).thenReturn(procedureTypeModel);

        procedureTypeService.registerProcedureType(procedureTypeCreationDataVo);

        assertEquals(procedureTypeCreationDataVo.getUserId(), userVo.getUserId());
        assertEquals(procedureTypeCreationDataVo.getName(), procedureTypeVo.getName());
        assertEquals(procedureTypeCreationDataVo.getPercentage(), procedureTypeVo.getPercentage());
        assertEquals(procedureTypeModel.getProcedureTypeId(), procedureTypeVo.getProcedureTypeId());
        assertNull(procedureTypeModel.getProcedureTypeId());

        verify(procedureTypeRepository).save(procedureTypeModel);
        verify(procedureTypeRepository).findByUser_userIdAndNameIgnoreCase(
                procedureTypeCreationDataVo.getUserId(), procedureTypeCreationDataVo.getName()
        );
        verify(procedureTypeMapper).voToModel(procedureTypeVo);
        verify(userService).getUser(procedureTypeCreationDataVo.getUserId());

    }

    @Test
    void givenProcedureTypeVoWhenCallRegisterProcedureTypeThenReEnableAndSaveProcedureType() {

        ProcedureTypeCreationDataVo procedureTypeCreationDataVo = getProcedureTypeCreationDataVo();
        Optional<ProcedureTypeModel> optionalProcedureTypeModel = getProcedureTypeModelOptionalWithDisableTrue();
        ProcedureTypeVo procedureTypeVo = getProcedureTypeVo();
        ProcedureTypeModel procedureTypeModel = getProcedureTypeModelWithDisableTrue();
        UserVo userVo = getUserVo();

        when(procedureTypeRepository.findByUser_userIdAndNameIgnoreCase(
                procedureTypeCreationDataVo.getUserId(), procedureTypeCreationDataVo.getName())
        ).thenReturn(optionalProcedureTypeModel);
        when(userService.getUser(procedureTypeCreationDataVo.getUserId())).thenReturn(userVo);
        when(procedureTypeMapper.voToModel(procedureTypeVo)).thenReturn(procedureTypeModel);

        procedureTypeService.registerProcedureType(procedureTypeCreationDataVo);

        assertEquals(procedureTypeCreationDataVo.getUserId(), userVo.getUserId());
        assertEquals(procedureTypeCreationDataVo.getName(), procedureTypeVo.getName());
        assertEquals(procedureTypeCreationDataVo.getPercentage(), procedureTypeVo.getPercentage());
        assertEquals(procedureTypeModel.getProcedureTypeId(), procedureTypeVo.getProcedureTypeId());
        assertNotNull(procedureTypeModel.getProcedureTypeId());

        verify(procedureTypeRepository).save(procedureTypeModel);
        verify(procedureTypeRepository).findByUser_userIdAndNameIgnoreCase(
                procedureTypeCreationDataVo.getUserId(), procedureTypeCreationDataVo.getName()
        );
        verify(procedureTypeMapper).voToModel(procedureTypeVo);
        verify(userService).getUser(procedureTypeCreationDataVo.getUserId());

    }

    @Test
    void givenProcedureTypeVoWhenCallRegisterProcedureTypeThenThrowDatabaseException() {

        Optional<ProcedureTypeModel> optionalProcedureTypeModel = getProcedureTypeModelOptional();
        ProcedureTypeCreationDataVo procedureTypeCreationDataVo = getProcedureTypeCreationDataVo();

        when(procedureTypeRepository.findByUser_userIdAndNameIgnoreCase(
                procedureTypeCreationDataVo.getUserId(), procedureTypeCreationDataVo.getName())
        ).thenReturn(optionalProcedureTypeModel);

        DatabaseException databaseException = assertThrows(DatabaseException.class,
                () -> procedureTypeService.registerProcedureType(procedureTypeCreationDataVo));

        assertEquals(databaseException.getMessage(), DATABASE_PROCEDURE_TYPE_ALREADY_EXISTS.getMessageKey());

        verify(procedureTypeRepository).findByUser_userIdAndNameIgnoreCase(id, name);

    }

    @Test
    void givenIdWhenCallGetProcedureTypeThenReturnProcedureType() {

        ProcedureTypeModel procedureTypeModel = getProcedureTypeModel();
        Optional<ProcedureTypeModel> optionalProcedureTypeModel = getProcedureTypeModelOptional();
        ProcedureTypeVo procedureTypeVo = getProcedureTypeVo();

        when(procedureTypeRepository.findById(id)).thenReturn(optionalProcedureTypeModel);
        when(procedureTypeMapper.modelToVo(optionalProcedureTypeModel.get())).thenReturn(procedureTypeVo);

        ProcedureTypeVo result = procedureTypeService.getProcedureType(id);

        assertEquals(result.getProcedureTypeId(), procedureTypeModel.getProcedureTypeId());
        assertEquals(result.getName(), procedureTypeModel.getName());
        assertEquals(result.getPercentage(), procedureTypeModel.getPercentage());
        assertEquals(result.getDisabled(), procedureTypeModel.getDisabled());

        verify(procedureTypeMapper).modelToVo(optionalProcedureTypeModel.get());

    }

    @Test
    void givenIdWhenCallGetProcedureTypeThenThrowDatabaseException() {

        Optional<ProcedureTypeModel> optionalProcedureTypeModel = getEmptyProcedureTypeModelOptional();

        when(procedureTypeRepository.findById(id)).thenReturn(optionalProcedureTypeModel);

        DatabaseException databaseException = assertThrows(DatabaseException.class,
                () -> procedureTypeService.getProcedureType(id));

        assertEquals(databaseException.getMessage(), DATABASE_PROCEDURE_TYPE_DOES_NOT_EXIST.getMessageKey());

        verify(procedureTypeRepository).findById(any());

    }

    @Test
    void givenProcedureTypeListVoWhenCallGetProcedureTypeListThenReturnProcedureTypeList() {

        Optional<List<ProcedureTypeModel>> optionalProcedureTypeListModel = getProcedureTypeModelListOptional();
        List<ProcedureTypeVo> procedureTypeVoList = getProcedureTypeVoList();

        when(procedureTypeRepository.findByUser_userIdAndDisabledIsFalseOrderByNameAsc(id)).thenReturn(optionalProcedureTypeListModel);
        when(procedureTypeMapper.modelListToVoList(optionalProcedureTypeListModel.get())).thenReturn(procedureTypeVoList);

        List<ProcedureTypeVo> result = procedureTypeService.getProcedureTypeListByUser(id);

        assertEquals(result.size(), optionalProcedureTypeListModel.get().size());
        assertEquals(result.get(0).getProcedureTypeId(), optionalProcedureTypeListModel.get().get(0).getProcedureTypeId());
        assertEquals(result.get(0).getName(), optionalProcedureTypeListModel.get().get(0).getName());
        assertEquals(result.get(0).getPercentage(), optionalProcedureTypeListModel.get().get(0).getPercentage());
        assertEquals(result.get(0).getDisabled(), optionalProcedureTypeListModel.get().get(0).getDisabled());

        verify(procedureTypeRepository).findByUser_userIdAndDisabledIsFalseOrderByNameAsc(id);
        verify(procedureTypeMapper).modelListToVoList(anyList());

    }

    @Test
    void givenProcedureTypeListVoWhenCallGetProcedureTypeListThenThrowDatabaseException() {

        Optional<List<ProcedureTypeModel>> optionalProcedureTypeListModel = getEmptyProcedureTypeModelListOptional();

        when(procedureTypeRepository.findByUser_userIdAndDisabledIsFalseOrderByNameAsc(id)).thenReturn(optionalProcedureTypeListModel);

        DatabaseException databaseException = assertThrows(DatabaseException.class,
                () -> procedureTypeService.getProcedureTypeListByUser(id));

        assertEquals(databaseException.getMessage(), DATABASE_PROCEDURE_TYPE_LIST_DOES_NOT_EXIST.getMessageKey());

        verify(procedureTypeRepository).findByUser_userIdAndDisabledIsFalseOrderByNameAsc(id);

    }

    @Test
    void givenIdWhenCallDeleteProcedureTypeThenDeleteProcedureType() {

        Optional<ProcedureTypeModel> optionalProcedureTypeModel = getProcedureTypeModelOptional();
        ProcedureTypeVo procedureTypeVo = getProcedureTypeVo();
        ProcedureTypeModel procedureTypeModel = getProcedureTypeModelWithDisableTrue();

        when(procedureTypeRepository.findById(id)).thenReturn(optionalProcedureTypeModel);
        when(procedureTypeMapper.modelToVo(any())).thenReturn(procedureTypeVo);
        when(procedureTypeMapper.voToModel(any())).thenReturn(procedureTypeModel);

        procedureTypeService.disableProcedureType(id);

        assertEquals(procedureTypeModel.getProcedureTypeId(), procedureTypeVo.getProcedureTypeId());
        assertEquals(procedureTypeModel.getName(), procedureTypeVo.getName());
        assertEquals(procedureTypeModel.getPercentage(), procedureTypeVo.getPercentage());
        assertEquals(TRUE, procedureTypeModel.getDisabled());

        verify(procedureTypeRepository).findById(id);
        verify(procedureTypeRepository).save(procedureTypeModel);
        verify(procedureTypeMapper).modelToVo(any());
        verify(procedureTypeMapper).voToModel(any());

    }

    @Test
    void givenProcedureTypeWhenCallEditProcedureTypeThenEditProcedureType() {

        Optional<ProcedureTypeModel> optionalProcedureTypeModel = getProcedureTypeModelOptional();
        ProcedureTypeVo procedureTypeVo = getProcedureTypeVo();
        ProcedureTypeModel procedureTypeModel = getProcedureTypeEditedModel();
        ProcedureTypeCreationDataVo procedureTypeCreationDataVo = getProcedureTypeCreationDataVo();

        when(procedureTypeRepository.findById(id)).thenReturn(optionalProcedureTypeModel);
        when(procedureTypeMapper.modelToVo(any())).thenReturn(procedureTypeVo);
        when(procedureTypeMapper.voToModel(any())).thenReturn(procedureTypeModel);

        procedureTypeService.editProcedureType(id, procedureTypeCreationDataVo);

        assertEquals(procedureTypeModel.getProcedureTypeId(), procedureTypeVo.getProcedureTypeId());
        assertNotEquals(procedureTypeModel.getName(), procedureTypeVo.getName());
        assertNotEquals(procedureTypeModel.getPercentage(), procedureTypeVo.getPercentage());

        verify(procedureTypeRepository).findById(id);
        verify(procedureTypeRepository).save(procedureTypeModel);
        verify(procedureTypeMapper).modelToVo(any());
        verify(procedureTypeMapper).voToModel(any());

    }

}
