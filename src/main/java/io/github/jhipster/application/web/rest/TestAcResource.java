package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.TestAc;
import io.github.jhipster.application.service.TestAcService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TestAc.
 */
@RestController
@RequestMapping("/api")
public class TestAcResource {

    private final Logger log = LoggerFactory.getLogger(TestAcResource.class);

    private static final String ENTITY_NAME = "testAc";

    private final TestAcService testAcService;

    public TestAcResource(TestAcService testAcService) {
        this.testAcService = testAcService;
    }

    /**
     * POST  /test-acs : Create a new testAc.
     *
     * @param testAc the testAc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new testAc, or with status 400 (Bad Request) if the testAc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/test-acs")
    @Timed
    public ResponseEntity<TestAc> createTestAc(@RequestBody TestAc testAc) throws URISyntaxException {
        log.debug("REST request to save TestAc : {}", testAc);
        if (testAc.getId() != null) {
            throw new BadRequestAlertException("A new testAc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TestAc result = testAcService.save(testAc);
        return ResponseEntity.created(new URI("/api/test-acs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /test-acs : Updates an existing testAc.
     *
     * @param testAc the testAc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated testAc,
     * or with status 400 (Bad Request) if the testAc is not valid,
     * or with status 500 (Internal Server Error) if the testAc couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/test-acs")
    @Timed
    public ResponseEntity<TestAc> updateTestAc(@RequestBody TestAc testAc) throws URISyntaxException {
        log.debug("REST request to update TestAc : {}", testAc);
        if (testAc.getId() == null) {
            return createTestAc(testAc);
        }
        TestAc result = testAcService.save(testAc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, testAc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /test-acs : get all the testAcs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of testAcs in body
     */
    @GetMapping("/test-acs")
    @Timed
    public ResponseEntity<List<TestAc>> getAllTestAcs(Pageable pageable) {
        log.debug("REST request to get a page of TestAcs");
        Page<TestAc> page = testAcService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/test-acs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /test-acs/:id : get the "id" testAc.
     *
     * @param id the id of the testAc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the testAc, or with status 404 (Not Found)
     */
    @GetMapping("/test-acs/{id}")
    @Timed
    public ResponseEntity<TestAc> getTestAc(@PathVariable Long id) {
        log.debug("REST request to get TestAc : {}", id);
        TestAc testAc = testAcService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(testAc));
    }

    /**
     * DELETE  /test-acs/:id : delete the "id" testAc.
     *
     * @param id the id of the testAc to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/test-acs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTestAc(@PathVariable Long id) {
        log.debug("REST request to delete TestAc : {}", id);
        testAcService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/test-acs?query=:query : search for the testAc corresponding
     * to the query.
     *
     * @param query the query of the testAc search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/test-acs")
    @Timed
    public ResponseEntity<List<TestAc>> searchTestAcs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TestAcs for query {}", query);
        Page<TestAc> page = testAcService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/test-acs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
