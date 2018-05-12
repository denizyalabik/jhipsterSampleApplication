package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.History;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the History entity.
 */
public interface HistorySearchRepository extends ElasticsearchRepository<History, Long> {
}
