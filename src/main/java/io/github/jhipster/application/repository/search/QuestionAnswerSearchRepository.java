package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.QuestionAnswer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the QuestionAnswer entity.
 */
public interface QuestionAnswerSearchRepository extends ElasticsearchRepository<QuestionAnswer, Long> {
}
