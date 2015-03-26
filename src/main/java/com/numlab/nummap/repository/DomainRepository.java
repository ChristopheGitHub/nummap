package com.numlab.nummap.repository;

import com.numlab.nummap.domain.Domain;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by eisti on 3/24/15.
 */
public interface DomainRepository extends MongoRepository<Domain, String> {
    List<Domain> findAll();
    long deleteByName(String name);
    Optional<Domain> findByName(String name);
    Optional<Domain> findById(String id);
}
