package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Usersdemo;
import io.github.jhipster.application.repository.UsersdemoRepository;
import io.github.jhipster.application.repository.search.UsersdemoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Usersdemo.
 */
@Service
@Transactional
public class UsersdemoService {

    private final Logger log = LoggerFactory.getLogger(UsersdemoService.class);

    private final UsersdemoRepository usersdemoRepository;

    private final UsersdemoSearchRepository usersdemoSearchRepository;

    public UsersdemoService(UsersdemoRepository usersdemoRepository, UsersdemoSearchRepository usersdemoSearchRepository) {
        this.usersdemoRepository = usersdemoRepository;
        this.usersdemoSearchRepository = usersdemoSearchRepository;
    }

    /**
     * Save a usersdemo.
     *
     * @param usersdemo the entity to save
     * @return the persisted entity
     */
    public Usersdemo save(Usersdemo usersdemo) {
        log.debug("Request to save Usersdemo : {}", usersdemo);
        Usersdemo result = usersdemoRepository.save(usersdemo);
        usersdemoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the usersdemos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Usersdemo> findAll(Pageable pageable) {
        log.debug("Request to get all Usersdemos");
        return usersdemoRepository.findAll(pageable);
    }

    /**
     * Get one usersdemo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Usersdemo findOne(Long id) {
        log.debug("Request to get Usersdemo : {}", id);
        return usersdemoRepository.findOne(id);
    }

    /**
     * Delete the usersdemo by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Usersdemo : {}", id);
        usersdemoRepository.delete(id);
        usersdemoSearchRepository.delete(id);
    }

    /**
     * Search for the usersdemo corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Usersdemo> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Usersdemos for query {}", query);
        Page<Usersdemo> result = usersdemoSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
