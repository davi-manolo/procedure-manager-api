package com.procedure.manager.service;

import com.procedure.manager.service.impl.ProcedureServiceImpl;
import com.procedure.manager.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class TaskServiceUnitTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private ProcedureServiceImpl procedureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenScheduledCronWhenCallCleanProceduresAfterThreeMonthsThenCleanProcedures() {
        taskService.cleanProceduresAfterThreeMonths();
        verify(procedureService).deleteProcedureWithThreeMonths();
    }

}
