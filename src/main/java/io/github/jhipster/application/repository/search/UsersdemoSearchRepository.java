package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Usersdemo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Usersdemo entity.
 */
public interface UsersdemoSearchRepository extends ElasticsearchRepository<Usersdemo, Long> {
}
