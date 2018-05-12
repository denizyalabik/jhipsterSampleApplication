package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.QuestionAnswer;
import io.github.jhipster.application.repository.QuestionAnswerRepository;
import io.github.jhipster.application.repository.search.QuestionAnswerSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing QuestionAnswer.
 */
@Service
@Transactional
public class QuestionAnswerService {

    private final Logger log = LoggerFactory.getLogger(QuestionAnswerService.class);

    private final QuestionAnswerRepository questionAnswerRepository;

    private final QuestionAnswerSearchRepository questionAnswerSearchRepository;

    public QuestionAnswerService(QuestionAnswerRepository questionAnswerRepository, QuestionAnswerSearchRepository questionAnswerSearchRepository) {
        this.questionAnswerRepository = questionAnswerRepository;
        this.questionAnswerSearchRepository = questionAnswerSearchRepository;
    }

    /**
     * Save a questionAnswer.
     *
     * @param questionAnswer the entity to save
     * @return the persisted entity
     */
    public QuestionAnswer save(QuestionAnswer questionAnswer) {
        log.debug("Request to save QuestionAnswer : {}", questionAnswer);
        QuestionAnswer result = questionAnswerRepository.save(questionAnswer);
        questionAnswerSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the questionAnswers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<QuestionAnswer> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionAnswers");
        return questionAnswerRepository.findAll(pageable);
    }

    /**
     * Get one questionAnswer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public QuestionAnswer findOne(Long id) {
        log.debug("Request to get QuestionAnswer : {}", id);
        return questionAnswerRepository.findOne(id);
    }

    /**
     * Delete the questionAnswer by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete QuestionAnswer : {}", id);
        questionAnswerRepository.delete(id);
        questionAnswerSearchRepository.delete(id);
    }

    /**
     * Search for the questionAnswer corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<QuestionAnswer> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of QuestionAnswers for query {}", query);
        Page<QuestionAnswer> result = questionAnswerSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
