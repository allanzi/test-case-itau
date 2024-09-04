package com.itau.test.repository;

import com.itau.test.domain.entity.QuoteEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.lang.NonNull;

public interface QuoteRepository extends MongoRepository<QuoteEntity, String> {
    @NonNull
    List<QuoteEntity> findAll();

    @Query("{ '_id': ?0 }")
    Optional<QuoteEntity> findOne(long id);
}