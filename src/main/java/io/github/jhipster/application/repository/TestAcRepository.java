package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.TestAc;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the TestAc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TestAcRepository extends JpaRepository<TestAc, Long> {
    @Query("select distinct test_ac from TestAc test_ac left join fetch test_ac.have")
    List<TestAc> findAllWithEagerRelationships();

    @Query("select test_ac from TestAc test_ac left join fetch test_ac.have where test_ac.id =:id")
    TestAc findOneWithEagerRelationships(@Param("id") Long id);

}
