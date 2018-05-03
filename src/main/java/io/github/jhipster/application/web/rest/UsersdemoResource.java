package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Usersdemo;
import io.github.jhipster.application.service.UsersdemoService;
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
 * REST controller for managing Usersdemo.
 */
@RestController
@RequestMapping("/api")
public class UsersdemoResource {

    private final Logger log = LoggerFactory.getLogger(UsersdemoResource.class);

    private static final String ENTITY_NAME = "usersdemo";

    private final UsersdemoService usersdemoService;

    public UsersdemoResource(UsersdemoService usersdemoService) {
        this.usersdemoService = usersdemoService;
    }

    /**
     * POST  /usersdemos : Create a new usersdemo.
     *
     * @param usersdemo the usersdemo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new usersdemo, or with status 400 (Bad Request) if the usersdemo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/usersdemos")
    @Timed
    public ResponseEntity<Usersdemo> createUsersdemo(@RequestBody Usersdemo usersdemo) throws URISyntaxException {
        log.debug("REST request to save Usersdemo : {}", usersdemo);
        if (usersdemo.getId() != null) {
            throw new BadRequestAlertException("A new usersdemo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Usersdemo result = usersdemoService.save(usersdemo);
        return ResponseEntity.created(new URI("/api/usersdemos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /usersdemos : Updates an existing usersdemo.
     *
     * @param usersdemo the usersdemo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated usersdemo,
     * or with status 400 (Bad Request) if the usersdemo is not valid,
     * or with status 500 (Internal Server Error) if the usersdemo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/usersdemos")
    @Timed
    public ResponseEntity<Usersdemo> updateUsersdemo(@RequestBody Usersdemo usersdemo) throws URISyntaxException {
        log.debug("REST request to update Usersdemo : {}", usersdemo);
        if (usersdemo.getId() == null) {
            return createUsersdemo(usersdemo);
        }
        Usersdemo result = usersdemoService.save(usersdemo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, usersdemo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /usersdemos : get all the usersdemos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of usersdemos in body
     */
    @GetMapping("/usersdemos")
    @Timed
    public ResponseEntity<List<Usersdemo>> getAllUsersdemos(Pageable pageable) {
        log.debug("REST request to get a page of Usersdemos");
        Page<Usersdemo> page = usersdemoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/usersdemos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /usersdemos/:id : get the "id" usersdemo.
     *
     * @param id the id of the usersdemo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the usersdemo, or with status 404 (Not Found)
     */
    @GetMapping("/usersdemos/{id}")
    @Timed
    public ResponseEntity<Usersdemo> getUsersdemo(@PathVariable Long id) {
        log.debug("REST request to get Usersdemo : {}", id);
        Usersdemo usersdemo = usersdemoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(usersdemo));
    }

    /**
     * DELETE  /usersdemos/:id : delete the "id" usersdemo.
     *
     * @param id the id of the usersdemo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/usersdemos/{id}")
    @Timed
    public ResponseEntity<Void> deleteUsersdemo(@PathVariable Long id) {
        log.debug("REST request to delete Usersdemo : {}", id);
        usersdemoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/usersdemos?query=:query : search for the usersdemo corresponding
     * to the query.
     *
     * @param query the query of the usersdemo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/usersdemos")
    @Timed
    public ResponseEntity<List<Usersdemo>> searchUsersdemos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Usersdemos for query {}", query);
        Page<Usersdemo> page = usersdemoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/usersdemos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
