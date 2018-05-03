package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Usersdemo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Usersdemo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsersdemoRepository extends JpaRepository<Usersdemo, Long> {

}
