package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.AcademiaUsers;
import io.github.jhipster.application.repository.AcademiaUsersRepository;
import io.github.jhipster.application.repository.search.AcademiaUsersSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AcademiaUsers.
 */
@Service
@Transactional
public class AcademiaUsersService {

    private final Logger log = LoggerFactory.getLogger(AcademiaUsersService.class);

    private final AcademiaUsersRepository academiaUsersRepository;

    private final AcademiaUsersSearchRepository academiaUsersSearchRepository;

    public AcademiaUsersService(AcademiaUsersRepository academiaUsersRepository, AcademiaUsersSearchRepository academiaUsersSearchRepository) {
        this.academiaUsersRepository = academiaUsersRepository;
        this.academiaUsersSearchRepository = academiaUsersSearchRepository;
    }

    /**
     * Save a academiaUsers.
     *
     * @param academiaUsers the entity to save
     * @return the persisted entity
     */
    public AcademiaUsers save(AcademiaUsers academiaUsers) {
        log.debug("Request to save AcademiaUsers : {}", academiaUsers);
        AcademiaUsers result = academiaUsersRepository.save(academiaUsers);
        academiaUsersSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the academiaUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AcademiaUsers> findAll(Pageable pageable) {
        log.debug("Request to get all AcademiaUsers");
        return academiaUsersRepository.findAll(pageable);
    }

    /**
     * Get one academiaUsers by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AcademiaUsers findOne(Long id) {
        log.debug("Request to get AcademiaUsers : {}", id);
        return academiaUsersRepository.findOne(id);
    }

    /**
     * Delete the academiaUsers by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AcademiaUsers : {}", id);
        academiaUsersRepository.delete(id);
        academiaUsersSearchRepository.delete(id);
    }

    /**
     * Search for the academiaUsers corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AcademiaUsers> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AcademiaUsers for query {}", query);
        Page<AcademiaUsers> result = academiaUsersSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
