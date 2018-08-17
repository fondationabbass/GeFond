package com.bdi.fondation.repository;

import com.bdi.fondation.domain.Caisse;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Caisse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaisseRepository extends JpaRepository<Caisse, Long>, JpaSpecificationExecutor<Caisse> {

    @Query("select caisse from Caisse caisse where caisse.user.login = ?#{principal.username}")
    List<Caisse> findByUserIsCurrentUser();

}
