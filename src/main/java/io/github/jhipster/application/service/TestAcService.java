package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.TestAc;
import io.github.jhipster.application.repository.TestAcRepository;
import io.github.jhipster.application.repository.search.TestAcSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TestAc.
 */
@Service
@Transactional
public class TestAcService {

    private final Logger log = LoggerFactory.getLogger(TestAcService.class);

    private final TestAcRepository testAcRepository;

    private final TestAcSearchRepository testAcSearchRepository;

    public TestAcService(TestAcRepository testAcRepository, TestAcSearchRepository testAcSearchRepository) {
        this.testAcRepository = testAcRepository;
        this.testAcSearchRepository = testAcSearchRepository;
    }

    /**
     * Save a testAc.
     *
     * @param testAc the entity to save
     * @return the persisted entity
     */
    public TestAc save(TestAc testAc) {
        log.debug("Request to save TestAc : {}", testAc);
        TestAc result = testAcRepository.save(testAc);
        testAcSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the testAcs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TestAc> findAll(Pageable pageable) {
        log.debug("Request to get all TestAcs");
        return testAcRepository.findAll(pageable);
    }

    /**
     * Get one testAc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TestAc findOne(Long id) {
        log.debug("Request to get TestAc : {}", id);
        return testAcRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the testAc by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TestAc : {}", id);
        testAcRepository.delete(id);
        testAcSearchRepository.delete(id);
    }

    /**
     * Search for the testAc corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TestAc> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TestAcs for query {}", query);
        Page<TestAc> result = testAcSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
