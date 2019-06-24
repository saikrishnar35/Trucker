package com.sk.egen.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sk.egen.entity.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, String> {

    Optional<Vehicle> findByVin(String vin);
}
