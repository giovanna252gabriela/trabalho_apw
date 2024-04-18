package com.example.tabelafipe.repository;

import com.example.tabelafipe.model.FipeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FipeRepository extends MongoRepository<FipeEntity, String> {
}
