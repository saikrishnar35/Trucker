package com.sk.egen.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rg.egen.entity.Reading;

@Repository
public interface ReadingsRepository extends CrudRepository<Reading, String> {

}
