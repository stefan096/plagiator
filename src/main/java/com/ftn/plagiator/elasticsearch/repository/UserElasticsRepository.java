package com.ftn.plagiator.elasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.ftn.plagiator.elasticsearch.model.UserElastics;

@Repository
public interface UserElasticsRepository extends ElasticsearchRepository<UserElastics, Long> {
}
