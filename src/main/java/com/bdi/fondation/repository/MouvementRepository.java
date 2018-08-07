package com.bdi.fondation.repository;

import com.bdi.fondation.domain.Mouvement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Mouvement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MouvementRepository extends JpaRepository<Mouvement, Long>, JpaSpecificationExecutor<Mouvement> {

    @Query(value = "select distinct mouvement from Mouvement mouvement left join fetch mouvement.echeances",
        countQuery = "select count(distinct mouvement) from Mouvement mouvement")
    Page<Mouvement> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct mouvement from Mouvement mouvement left join fetch mouvement.echeances")
    List<Mouvement> findAllWithEagerRelationships();

    @Query("select mouvement from Mouvement mouvement left join fetch mouvement.echeances where mouvement.id =:id")
    Optional<Mouvement> findOneWithEagerRelationships(@Param("id") Long id);

}
