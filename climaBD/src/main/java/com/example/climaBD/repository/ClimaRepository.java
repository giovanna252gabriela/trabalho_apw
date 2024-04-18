package com.example.climaBD.repository;

import com.example.climaBD.model.ClimaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClimaRepository extends MongoRepository<ClimaEntity, String> {

}
