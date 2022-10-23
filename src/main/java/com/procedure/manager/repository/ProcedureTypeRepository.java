package com.procedure.manager.repository;

import com.procedure.manager.domain.model.ProcedureTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcedureTypeRepository extends JpaRepository<ProcedureTypeModel, Long> {

    Optional<List<ProcedureTypeModel>> findByDisabledIsFalseOrderByNameAsc();

    Optional<ProcedureTypeModel> findByName(String name);

}
