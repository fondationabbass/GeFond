package com.bdi.fondation.repository;

import com.bdi.fondation.domain.Operation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Operation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationRepository extends JpaRepository<Operation, Long>, JpaSpecificationExecutor<Operation> {
    @Query("select distinct operation from Operation operation left join fetch operation.echeances")
    List<Operation> findAllWithEagerRelationships();

    @Query("select operation from Operation operation left join fetch operation.echeances where operation.id =:id")
    Operation findOneWithEagerRelationships(@Param("id") Long id);

}
