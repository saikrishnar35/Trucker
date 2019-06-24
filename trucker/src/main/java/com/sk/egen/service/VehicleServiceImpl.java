package com.sk.egen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.egen.entity.Vehicle;
import com.sk.egen.exception.VehicleNotFoundException;
import com.sk.egen.repository.VehicleRepository;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repository;

    @Autowired
    public VehicleServiceImpl(VehicleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Vehicle> findAll() {
        return (List<Vehicle>) repository.findAll();
    }

    @Override
    public Vehicle findOne(String vin) {
        Optional<Vehicle> existing = repository.findByVin(vin);
        return existing.orElseThrow(() -> new VehicleNotFoundException("Vehicle with VIN:- "+vin+" not found"));
    }

    @Override
    public Vehicle create(Vehicle v) {
        return null;
    }

    @Override
    public List<Vehicle> update(List<Vehicle> vList) {
        vList.forEach(v -> {
            if(!repository.findByVin(v.getVin()).isPresent()) {
                System.out.println(v.getLastServiceDate());
            }
        });
        return (List<Vehicle>) repository.saveAll(vList);
    }

    @Override
    public Vehicle delete(String vin) {
        return null;
    }

}
