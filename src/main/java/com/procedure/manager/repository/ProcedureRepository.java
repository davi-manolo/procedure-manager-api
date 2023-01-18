package com.procedure.manager.repository;

import com.procedure.manager.domain.model.ProcedureModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("all")
public interface ProcedureRepository extends JpaRepository<ProcedureModel, Long> {

    Optional<List<ProcedureModel>> findByRegistrationDateBetweenAndUser_userIdEqualsAndDisabledIsFalseOrderByRegistrationDateAsc(
            LocalDateTime startDate, LocalDateTime endDate, Long userId);

}
