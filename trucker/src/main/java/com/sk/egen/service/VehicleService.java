package com.sk.egen.service;

import java.util.List;

import com.sk.egen.entity.Vehicle;

public interface VehicleService {

    List<Vehicle> findAll();

    Vehicle findOne(String vin);

    Vehicle create(Vehicle v);

    Iterable<Vehicle> update(List<Vehicle> vList);

    Vehicle delete(String vin);

}
