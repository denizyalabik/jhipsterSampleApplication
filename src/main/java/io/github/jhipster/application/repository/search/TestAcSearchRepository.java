package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.TestAc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TestAc entity.
 */
public interface TestAcSearchRepository extends ElasticsearchRepository<TestAc, Long> {
}
