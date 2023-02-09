package com.procedure.manager.service.impl;

import com.procedure.manager.service.ProcedureService;
import com.procedure.manager.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@SuppressWarnings("unused")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private ProcedureService procedureService;

    @Override
    @Scheduled(cron = "0 0 0 1 * ?", zone = "America/Sao_Paulo")
    public void cleanProceduresAfterThreeMonths() {
        log.info("Task de limpeza de prodecimentos iniciado.");
        procedureService.deleteProcedureWithThreeMonths();
    }

}
