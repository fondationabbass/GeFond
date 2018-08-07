package com.bdi.fondation.repository;

import com.bdi.fondation.domain.Compte;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Compte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteRepository extends JpaRepository<Compte, Long>, JpaSpecificationExecutor<Compte> {

    @Query("select compte from Compte compte where compte.user.login = ?#{principal.username}")
    List<Compte> findByUserIsCurrentUser();

}
