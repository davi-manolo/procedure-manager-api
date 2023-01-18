package com.procedure.manager.service;

import com.procedure.manager.domain.exception.DatabaseException;
import com.procedure.manager.domain.mapper.ProcedureMapper;
import com.procedure.manager.domain.model.ProcedureModel;
import com.procedure.manager.domain.vo.*;
import com.procedure.manager.repository.ProcedureRepository;
import com.procedure.manager.service.impl.ProcedureServiceImpl;
import com.procedure.manager.service.impl.ProcedureTypeServiceImpl;
import com.procedure.manager.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static com.procedure.manager.domain.enumeration.ExceptionMessage.DATABASE_PROCEDURE_DOES_NOT_EXIST;
import static com.procedure.manager.domain.enumeration.ExceptionMessage.DATABASE_PROCEDURE_LIST_DOES_NOT_EXIST;
import static com.procedure.manager.domain.mother.ProcedureMother.*;
import static com.procedure.manager.domain.mother.ProcedureTypeMother.getProcedureTypeVo;
import static com.procedure.manager.domain.mother.UserMother.getUserVo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class ProcedureServiceUnitTest {

    @InjectMocks
    private ProcedureServiceImpl procedureService;

    @Mock
    private ProcedureTypeServiceImpl procedureTypeService;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private ProcedureMapper procedureMapper;

    @Mock
    private ProcedureRepository procedureRepository;

    private Long procedureTypeId;
    private Long procedureId;
    private DataSearchProcedureMonthVo dataSearchProcedureMonthVo;
    private Long userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.procedureTypeId = 1L;
        this.dataSearchProcedureMonthVo = new DataSearchProcedureMonthVo(9, 2023, 1L);
        this.procedureId = 1L;
        this.userId = 1L;
    }

    @Test
    void givenDataToCreateProcedureWhenCallRegisterProcedureThenSaveProcedure() {

        ProcedureCreationDataVo procedureCreationDataVo = getDataToCreateProcedureVo();
        ProcedureTypeVo procedureTypeVo = getProcedureTypeVo();
        ProcedureModel procedureModel = getProcedureModel();
        UserVo userVo = getUserVo();

        when(procedureTypeService.getProcedureType(procedureTypeId)).thenReturn(procedureTypeVo);
        when(userService.getUser(userId)).thenReturn(userVo);
        when(procedureMapper.voToModel(any())).thenReturn(procedureModel);
        when(procedureRepository.save(any())).thenReturn(procedureModel);

        procedureService.registerProcedure(procedureCreationDataVo);

        assertEquals("Cliente Beltrano", procedureModel.getCustomer());
        assertEquals(BigDecimal.valueOf(800.00), procedureModel.getValue());
        assertEquals(BigDecimal.valueOf(240.00), procedureModel.getValueReceived());
        assertEquals("Tipo de Procedimento", procedureModel.getProcedureType().getName());
        assertEquals(30.00, procedureModel.getProcedureType().getPercentage());
        assertEquals("Fulano", procedureModel.getUser().getName());
        assertEquals("Ciclano", procedureModel.getUser().getSurName());
        assertEquals(LocalDate.of(2022, Month.SEPTEMBER, 5), procedureModel.getProcedureDate());

        verify(procedureTypeService).getProcedureType(procedureTypeId);
        verify(userService).getUser(userId);
        verify(procedureMapper).voToModel(any());
        verify(procedureRepository).save(procedureModel);

    }

    @Test
    void givenMonthYearUserIdWhenCallGetProcedureListByPeriodThenThrowDatabaseException() {

        Optional<List<ProcedureModel>> optionalProcedureModel = getProcedureModelListOptional();
        List<ProcedureVo> procedureVoList = getProcedureVoList();

        when(procedureRepository
                .findByRegistrationDateBetweenAndUser_userIdEqualsAndDisabledIsFalseOrderByRegistrationDateAsc(
                        any(), any(), any()))
                .thenReturn(optionalProcedureModel);
        when(procedureMapper.modelListToVoList(optionalProcedureModel.get())).thenReturn(procedureVoList);

        List<ProcedureVo> result = procedureService.getProcedureListByPeriod(dataSearchProcedureMonthVo);

        assertEquals(result.size(), optionalProcedureModel.get().size());
        assertEquals(result.get(0).getProcedureId(), optionalProcedureModel.get().get(0).getProcedureId());
        assertEquals(result.get(0).getProcedureDate(), optionalProcedureModel.get().get(0).getProcedureDate());
        assertEquals(result.get(0).getCustomer(), optionalProcedureModel.get().get(0).getCustomer());
        assertEquals(result.get(0).getProcedureType().getName(), optionalProcedureModel.get().get(0).getProcedureType().getName());
        assertEquals(result.get(0).getValue(), optionalProcedureModel.get().get(0).getValue());
        assertEquals(result.get(0).getValueReceived(), optionalProcedureModel.get().get(0).getValueReceived());

        verify(procedureRepository).findByRegistrationDateBetweenAndUser_userIdEqualsAndDisabledIsFalseOrderByRegistrationDateAsc(any(), any(),any());
        verify(procedureMapper).modelListToVoList(anyList());

    }

    @Test
    void givenMonthYearUserIdWhenCallGetProcedureListByPeriodThenReturnProcedureList() {

        Optional<List<ProcedureModel>> optionalProcedureModel = getEmptyProcedureModelListOptional();

        when(procedureRepository
                .findByRegistrationDateBetweenAndUser_userIdEqualsAndDisabledIsFalseOrderByRegistrationDateAsc(
                        any(), any(), any()))
                .thenReturn(optionalProcedureModel);

        DatabaseException databaseException = assertThrows(DatabaseException.class,
                () -> procedureService.getProcedureListByPeriod(dataSearchProcedureMonthVo));

        assertEquals(databaseException.getMessage(), DATABASE_PROCEDURE_LIST_DOES_NOT_EXIST.getMessageKey());

        verify(procedureRepository).findByRegistrationDateBetweenAndUser_userIdEqualsAndDisabledIsFalseOrderByRegistrationDateAsc(any(), any(), any());

    }

    @Test
    void givenProcedureIdWhenCallGetProcedureThenReturnProcedure() {

        Optional<ProcedureModel> optionalProcedureModel = getProcedureModelOptional();
        ProcedureVo procedureVo = getProcedureVo();

        when(procedureRepository.findById(procedureId)).thenReturn(optionalProcedureModel);
        when(procedureMapper.modelToVo(optionalProcedureModel.get())).thenReturn(procedureVo);

        ProcedureVo result = procedureService.getProcedure(procedureId);

        assertEquals(result.getProcedureId(), optionalProcedureModel.get().getProcedureId());
        assertEquals(result.getProcedureDate(), optionalProcedureModel.get().getProcedureDate());
        assertEquals(result.getCustomer(), optionalProcedureModel.get().getCustomer());
        assertEquals(result.getProcedureType().getName(), optionalProcedureModel.get().getProcedureType().getName());
        assertEquals(result.getValue(), optionalProcedureModel.get().getValue());
        assertEquals(result.getValueReceived(), optionalProcedureModel.get().getValueReceived());

        verify(procedureMapper).modelToVo(optionalProcedureModel.get());

    }
    @Test
    void givenProcedureIdWhenCallGetProcedureThenThrowDatabaseException() {

        Optional<ProcedureModel> optionalProcedureModel = getEmptyProcedureModelOptional();

        when(procedureRepository.findById(procedureId)).thenReturn(optionalProcedureModel);

        DatabaseException databaseException = assertThrows(DatabaseException.class,
                () -> procedureService.getProcedure(procedureId));

        assertEquals(databaseException.getMessage(), DATABASE_PROCEDURE_DOES_NOT_EXIST.getMessageKey());

        verify(procedureRepository).findById(any());

    }

    @Test
    void givenIdWhenCallDeleteProcedureThenDeleteProcedure() {

        Optional<ProcedureModel> optionalProcedureModel = getProcedureModelOptional();
        ProcedureVo procedureVo = getProcedureVo();
        ProcedureModel procedureModel = getProcedureDisabledModel();

        when(procedureRepository.findById(procedureId)).thenReturn(optionalProcedureModel);
        when(procedureMapper.modelToVo(any())).thenReturn(procedureVo);
        when(procedureMapper.voToModel(any())).thenReturn(procedureModel);

        procedureService.disableProcedure(procedureId);

        assertEquals(procedureModel.getProcedureId(), procedureVo.getProcedureId());
        assertEquals(procedureModel.getCustomer(), procedureVo.getCustomer());
        assertEquals(procedureModel.getValue(), procedureVo.getValue());
        assertEquals(procedureModel.getValueReceived(), procedureVo.getValueReceived());
        assertTrue(procedureModel.getDisabled());

        verify(procedureRepository).findById(procedureId);
        verify(procedureRepository).save(procedureModel);
        verify(procedureMapper).modelToVo(any());
        verify(procedureMapper).voToModel(any());

    }

    @Test
    void givenIdAndDataProcedureWhenCallEditProcedureThenEditProcedure() {

        ProcedureCreationDataVo procedureCreationDataVo = getDataToCreateProcedureVo();
        ProcedureTypeVo procedureTypeVo = getProcedureTypeVo();
        ProcedureModel procedureModel = getProcedureModel();
        Optional<ProcedureModel> optionalProcedureModel = getProcedureModelOptional();
        ProcedureVo procedureVo = getProcedureVo();
        UserVo userVo = getUserVo();

        when(procedureTypeService.getProcedureType(procedureCreationDataVo.getProcedureTypeId())).thenReturn(procedureTypeVo);
        when(userService.getUser(procedureCreationDataVo.getUserId())).thenReturn(userVo);
        when(procedureRepository.findById(procedureId)).thenReturn(optionalProcedureModel);
        when(procedureMapper.modelToVo(any())).thenReturn(procedureVo);
        when(procedureMapper.voToModel(any())).thenReturn(procedureModel);
        when(procedureRepository.save(any())).thenReturn(procedureModel);

        procedureService.editProcedure(procedureId, procedureCreationDataVo);

        assertEquals("Cliente Beltrano", procedureModel.getCustomer());
        assertEquals(BigDecimal.valueOf(800.00), procedureModel.getValue());
        assertEquals(BigDecimal.valueOf(240.00), procedureModel.getValueReceived());
        assertEquals("Tipo de Procedimento", procedureModel.getProcedureType().getName());
        assertEquals(30.00, procedureModel.getProcedureType().getPercentage());
        assertEquals("Fulano", procedureModel.getUser().getName());
        assertEquals("Ciclano", procedureModel.getUser().getSurName());
        assertEquals(LocalDate.of(2022, Month.SEPTEMBER, 5), procedureModel.getProcedureDate());

        verify(procedureTypeService).getProcedureType(procedureTypeId);
        verify(userService).getUser(userId);
        verify(procedureMapper).voToModel(any());
        verify(procedureRepository).save(procedureModel);

    }

}
