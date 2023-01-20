package com.procedure.manager.repository;

import com.procedure.manager.domain.model.ProcedureTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("all")
public interface ProcedureTypeRepository extends JpaRepository<ProcedureTypeModel, Long> {

    Optional<List<ProcedureTypeModel>> findByUser_userIdAndDisabledIsFalseOrderByNameAsc(Long userId);

    Optional<ProcedureTypeModel> findByUser_userIdAndNameIgnoreCase(Long userId, String name);

}
