package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.History;
import io.github.jhipster.application.repository.HistoryRepository;
import io.github.jhipster.application.repository.search.HistorySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing History.
 */
@Service
@Transactional
public class HistoryService {

    private final Logger log = LoggerFactory.getLogger(HistoryService.class);

    private final HistoryRepository historyRepository;

    private final HistorySearchRepository historySearchRepository;

    public HistoryService(HistoryRepository historyRepository, HistorySearchRepository historySearchRepository) {
        this.historyRepository = historyRepository;
        this.historySearchRepository = historySearchRepository;
    }

    /**
     * Save a history.
     *
     * @param history the entity to save
     * @return the persisted entity
     */
    public History save(History history) {
        log.debug("Request to save History : {}", history);
        History result = historyRepository.save(history);
        historySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the histories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<History> findAll(Pageable pageable) {
        log.debug("Request to get all Histories");
        return historyRepository.findAll(pageable);
    }

    /**
     * Get one history by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public History findOne(Long id) {
        log.debug("Request to get History : {}", id);
        return historyRepository.findOne(id);
    }

    /**
     * Delete the history by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete History : {}", id);
        historyRepository.delete(id);
        historySearchRepository.delete(id);
    }

    /**
     * Search for the history corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<History> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Histories for query {}", query);
        Page<History> result = historySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
