package com.procedure.manager.service;

import com.procedure.manager.domain.exception.DatabaseException;
import com.procedure.manager.domain.mapper.ProcedureTypeMapper;
import com.procedure.manager.domain.model.ProcedureTypeModel;
import com.procedure.manager.domain.vo.ProcedureTypeVo;
import com.procedure.manager.repository.ProcedureTypeRepository;
import com.procedure.manager.service.impl.ProcedureTypeServiceImpl;
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
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
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
    private Long id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.id = 1L;
    }

    @Test
    void givenProcedureTypeVoWhenCallRegisterProcedureTypeThenSaveProcedureType() {

        ProcedureTypeVo procedureTypeVo = getProcedureTypeVo();
        ProcedureTypeModel procedureTypeModel = getProcedureTypeModel();

        when(procedureTypeRepository.save(any())).thenReturn(procedureTypeModel);
        when(procedureTypeMapper.voToModel(any())).thenReturn(procedureTypeModel);

        procedureTypeService.registerProcedureType(procedureTypeVo);

        assertEquals(1L, procedureTypeVo.getProcedureTypeId());
        assertEquals("Tipo de Procedimento", procedureTypeVo.getName());
        assertEquals(30.00, procedureTypeVo.getPercentage());
        assertEquals(FALSE, procedureTypeVo.getDisabled());

        verify(procedureTypeRepository).findByNameIgnoreCase(procedureTypeVo.getName());
        verify(procedureTypeMapper).voToModel(procedureTypeVo);
        verify(procedureTypeRepository).save(procedureTypeModel);

    }

    @Test
    void givenProcedureTypeVoWhenCallRegisterProcedureTypeThenReEnableAndSaveProcedureType() {

        ProcedureTypeVo procedureTypeVo = getProcedureTypeVo();
        ProcedureTypeModel procedureTypeModel = getProcedureTypeModelWithDisableTrue();
        Optional<ProcedureTypeModel> optionalProcedureTypeModel = getProcedureTypeModelOptionalWithDisableTrue();

        when(procedureTypeRepository.findByNameIgnoreCase(anyString())).thenReturn(optionalProcedureTypeModel);
        when(procedureTypeMapper.voToModel(any())).thenReturn(procedureTypeModel);

        procedureTypeService.registerProcedureType(procedureTypeVo);

        assertEquals(1L, procedureTypeVo.getProcedureTypeId());
        assertEquals("Tipo de Procedimento", procedureTypeVo.getName());
        assertEquals(30.00, procedureTypeVo.getPercentage());
        assertEquals(FALSE, procedureTypeVo.getDisabled());

        verify(procedureTypeRepository).findByNameIgnoreCase(procedureTypeVo.getName());
        verify(procedureTypeMapper).voToModel(procedureTypeVo);
        verify(procedureTypeRepository).save(procedureTypeModel);

    }

    @Test
    void givenProcedureTypeVoWhenCallRegisterProcedureTypeThenThrowDatabaseException() {

        ProcedureTypeVo procedureTypeVo = getProcedureTypeVo();
        Optional<ProcedureTypeModel> optionalProcedureTypeModel = getProcedureTypeModelOptional();

        when(procedureTypeRepository.findByNameIgnoreCase(anyString())).thenReturn(optionalProcedureTypeModel);

        DatabaseException databaseException = assertThrows(DatabaseException.class,
                () -> procedureTypeService.registerProcedureType(procedureTypeVo));

        assertEquals(databaseException.getMessage(), DATABASE_PROCEDURE_TYPE_ALREADY_EXISTS.getMessageKey());

        verify(procedureTypeRepository).findByNameIgnoreCase(any());

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

        when(procedureTypeRepository.findByDisabledIsFalseOrderByNameAsc()).thenReturn(optionalProcedureTypeListModel);
        when(procedureTypeMapper.modelListToVoList(optionalProcedureTypeListModel.get())).thenReturn(procedureTypeVoList);

        List<ProcedureTypeVo> result = procedureTypeService.getProcedureTypeList();

        assertTrue(optionalProcedureTypeListModel.isPresent());
        assertEquals(result.size(), optionalProcedureTypeListModel.get().size());
        assertEquals(result.get(0).getProcedureTypeId(), optionalProcedureTypeListModel.get().get(0).getProcedureTypeId());
        assertEquals(result.get(0).getName(), optionalProcedureTypeListModel.get().get(0).getName());
        assertEquals(result.get(0).getPercentage(), optionalProcedureTypeListModel.get().get(0).getPercentage());
        assertEquals(result.get(0).getDisabled(), optionalProcedureTypeListModel.get().get(0).getDisabled());

        verify(procedureTypeRepository).findByDisabledIsFalseOrderByNameAsc();
        verify(procedureTypeMapper).modelListToVoList(anyList());

    }

    @Test
    void givenProcedureTypeListVoWhenCallGetProcedureTypeListThenThrowDatabaseException() {

        Optional<List<ProcedureTypeModel>> optionalProcedureTypeListModel = getEmptyProcedureTypeModelListOptional();

        when(procedureTypeRepository.findByDisabledIsFalseOrderByNameAsc()).thenReturn(optionalProcedureTypeListModel);

        DatabaseException databaseException = assertThrows(DatabaseException.class,
                () -> procedureTypeService.getProcedureTypeList());

        assertEquals(databaseException.getMessage(), DATABASE_PROCEDURE_TYPE_LIST_DOES_NOT_EXIST.getMessageKey());

        verify(procedureTypeRepository).findByDisabledIsFalseOrderByNameAsc();

    }

    @Test
    void givenIdWhenCallDeleteProcedureTypeThenDeleteProcedureType() {

        Optional<ProcedureTypeModel> optionalProcedureTypeModel = getProcedureTypeModelOptional();
        ProcedureTypeVo procedureTypeVo = getProcedureTypeVo();
        ProcedureTypeModel procedureTypeModel = getProcedureTypeDisabledModel();

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

        when(procedureTypeRepository.findById(id)).thenReturn(optionalProcedureTypeModel);
        when(procedureTypeMapper.modelToVo(any())).thenReturn(procedureTypeVo);
        when(procedureTypeMapper.voToModel(any())).thenReturn(procedureTypeModel);

        procedureTypeService.editProcedureType(procedureTypeVo);

        assertEquals(procedureTypeModel.getProcedureTypeId(), procedureTypeVo.getProcedureTypeId());
        assertNotEquals(procedureTypeModel.getName(), procedureTypeVo.getName());
        assertNotEquals(procedureTypeModel.getPercentage(), procedureTypeVo.getPercentage());

        verify(procedureTypeRepository).findById(id);
        verify(procedureTypeRepository).save(procedureTypeModel);
        verify(procedureTypeMapper).modelToVo(any());
        verify(procedureTypeMapper).voToModel(any());

    }

}
