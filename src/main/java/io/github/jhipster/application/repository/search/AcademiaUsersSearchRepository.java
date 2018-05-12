package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.AcademiaUsers;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AcademiaUsers entity.
 */
public interface AcademiaUsersSearchRepository extends ElasticsearchRepository<AcademiaUsers, Long> {
}
