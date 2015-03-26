package com.numlab.nummap.repository;

import com.numlab.nummap.domain.Competence;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by eisti on 3/24/15.
 */
public interface CompetenceRepository extends MongoRepository<Competence, String> {
    List<Competence> findAll();
    long deleteByName(String name);
    Optional<Competence> findByName(String name);
    Optional<Competence> findById(String id);
}
