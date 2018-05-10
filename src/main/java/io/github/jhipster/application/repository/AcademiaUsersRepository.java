package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.AcademiaUsers;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AcademiaUsers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcademiaUsersRepository extends JpaRepository<AcademiaUsers, Long> {

}
