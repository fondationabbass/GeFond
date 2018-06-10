package com.bdi.fondation.repository;

import com.bdi.fondation.domain.Mouvement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Mouvement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MouvementRepository extends JpaRepository<Mouvement, Long> {
    @Query("select distinct mouvement from Mouvement mouvement left join fetch mouvement.echeances")
    List<Mouvement> findAllWithEagerRelationships();

    @Query("select mouvement from Mouvement mouvement left join fetch mouvement.echeances where mouvement.id =:id")
    Mouvement findOneWithEagerRelationships(@Param("id") Long id);

}
