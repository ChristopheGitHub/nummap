package com.numlab.nummap.repository;

import com.numlab.nummap.domain.School;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by christo on 26/04/15.
 */
public interface SchoolRepository extends MongoRepository<School, String> {

    List<School> findAll();

    Optional<School> findOneByName(String name);

    Optional <School> findOneById(String id);

    void delete(School school);

    long deleteByName(String name);
}
