package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.AcademiaUsers;
import io.github.jhipster.application.service.AcademiaUsersService;
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
 * REST controller for managing AcademiaUsers.
 */
@RestController
@RequestMapping("/api")
public class AcademiaUsersResource {

    private final Logger log = LoggerFactory.getLogger(AcademiaUsersResource.class);

    private static final String ENTITY_NAME = "academiaUsers";

    private final AcademiaUsersService academiaUsersService;

    public AcademiaUsersResource(AcademiaUsersService academiaUsersService) {
        this.academiaUsersService = academiaUsersService;
    }

    /**
     * POST  /academia-users : Create a new academiaUsers.
     *
     * @param academiaUsers the academiaUsers to create
     * @return the ResponseEntity with status 201 (Created) and with body the new academiaUsers, or with status 400 (Bad Request) if the academiaUsers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/academia-users")
    @Timed
    public ResponseEntity<AcademiaUsers> createAcademiaUsers(@RequestBody AcademiaUsers academiaUsers) throws URISyntaxException {
        log.debug("REST request to save AcademiaUsers : {}", academiaUsers);
        if (academiaUsers.getId() != null) {
            throw new BadRequestAlertException("A new academiaUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcademiaUsers result = academiaUsersService.save(academiaUsers);
        return ResponseEntity.created(new URI("/api/academia-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /academia-users : Updates an existing academiaUsers.
     *
     * @param academiaUsers the academiaUsers to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated academiaUsers,
     * or with status 400 (Bad Request) if the academiaUsers is not valid,
     * or with status 500 (Internal Server Error) if the academiaUsers couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/academia-users")
    @Timed
    public ResponseEntity<AcademiaUsers> updateAcademiaUsers(@RequestBody AcademiaUsers academiaUsers) throws URISyntaxException {
        log.debug("REST request to update AcademiaUsers : {}", academiaUsers);
        if (academiaUsers.getId() == null) {
            return createAcademiaUsers(academiaUsers);
        }
        AcademiaUsers result = academiaUsersService.save(academiaUsers);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, academiaUsers.getId().toString()))
            .body(result);
    }

    /**
     * GET  /academia-users : get all the academiaUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of academiaUsers in body
     */
    @GetMapping("/academia-users")
    @Timed
    public ResponseEntity<List<AcademiaUsers>> getAllAcademiaUsers(Pageable pageable) {
        log.debug("REST request to get a page of AcademiaUsers");
        Page<AcademiaUsers> page = academiaUsersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/academia-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /academia-users/:id : get the "id" academiaUsers.
     *
     * @param id the id of the academiaUsers to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the academiaUsers, or with status 404 (Not Found)
     */
    @GetMapping("/academia-users/{id}")
    @Timed
    public ResponseEntity<AcademiaUsers> getAcademiaUsers(@PathVariable Long id) {
        log.debug("REST request to get AcademiaUsers : {}", id);
        AcademiaUsers academiaUsers = academiaUsersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(academiaUsers));
    }

    /**
     * DELETE  /academia-users/:id : delete the "id" academiaUsers.
     *
     * @param id the id of the academiaUsers to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/academia-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteAcademiaUsers(@PathVariable Long id) {
        log.debug("REST request to delete AcademiaUsers : {}", id);
        academiaUsersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/academia-users?query=:query : search for the academiaUsers corresponding
     * to the query.
     *
     * @param query the query of the academiaUsers search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/academia-users")
    @Timed
    public ResponseEntity<List<AcademiaUsers>> searchAcademiaUsers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AcademiaUsers for query {}", query);
        Page<AcademiaUsers> page = academiaUsersService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/academia-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
