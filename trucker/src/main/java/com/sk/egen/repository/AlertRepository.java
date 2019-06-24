package com.sk.egen.repository;

import com.rg.egen.entity.Alert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends CrudRepository<Alert, String> {

    Iterable<Alert> findByVin(String vin);

    Iterable<Alert> findByPriority(String priority);
}
