package com.sk.egen.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rg.egen.entity.Alert;
import com.rg.egen.entity.Reading;
import com.rg.egen.entity.Vehicle;
import com.rg.egen.exception.ReadingNotFoundException;
import com.rg.egen.repository.AlertRepository;
import com.rg.egen.repository.ReadingsRepository;
import com.rg.egen.repository.VehicleRepository;

@Service
public class ReadingServiceImpl implements ReadingService {

    private final ReadingsRepository repository;

    private final VehicleRepository vehicleRepository;

    private final AlertRepository alertRepository;

    @Autowired
    public ReadingServiceImpl(ReadingsRepository repository, VehicleRepository vehicleRepository, AlertRepository alertRepository) {
        this.repository = repository;
        this.vehicleRepository = vehicleRepository;
        this.alertRepository = alertRepository;
    }

    @Override
    public List<Reading> findAll() {
        return (List<Reading>) repository.findAll();
    }

    @Override
    public Reading findOne(String id) {
        Optional<Reading> existing = repository.findById(id);
        if(!existing.isPresent()) {
            throw new ReadingNotFoundException("Reading with id:- "+id+" Not Found");
        }
        return existing.get();
    }

    @Override
    public Reading create(Reading reading) {
        Optional<Vehicle> existing = vehicleRepository.findByVin(reading.getVin());
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss.s");
        try {
            date = df.parse(reading.getTimestamp());
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        if(existing.isPresent()) {
            if(reading.getFuelVolume() < 0.1 * existing.get().getMaxFuelVolume()) {
                Alert alert = new Alert();
                alert.setVin(reading.getVin());
                alert.setPriority("MEDIUM");
                alert.setDescription("Low Fuel");
                alert.setTimestamp(date);
                alert.setLatitude(reading.getLatitude());
                alert.setLongitude(reading.getLongitude());
                alertRepository.save(alert);
            }
            if(reading.getEngineRpm() > existing.get().getRedlineRpm()) {
                Alert alert = new Alert();
                alert.setVin(reading.getVin());
                alert.setPriority("HIGH");
                alert.setDescription("High Engine Rpm");
                alert.setTimestamp(date);
                alert.setLatitude(reading.getLatitude());
                alert.setLongitude(reading.getLongitude());
                alertRepository.save(alert);
                System.out.println("Alert for Vehicle with VIN:- "+reading.getVin()+" with priority HIGH");
            }
            if(reading.getTires().getFrontLeft() < 32 || reading.getTires().getFrontLeft() > 36 || reading.getTires().getFrontRight() > 36 || reading.getTires().getFrontRight() < 32 || reading.getTires().getRearLeft() > 36 || reading.getTires().getRearLeft() < 32 || reading.getTires().getRearRight() > 36 || reading.getTires().getRearRight() < 32) {
                Alert alert = new Alert();
                alert.setVin(reading.getVin());
                alert.setPriority("LOW");
                alert.setDescription("High/Low Tire Pressure");
                alert.setTimestamp(date);
                alert.setLatitude(reading.getLatitude());
                alert.setLongitude(reading.getLongitude());
                alertRepository.save(alert);
                System.out.println("Alert for Vehicle with VIN:- "+reading.getVin()+" with priority LOW");
            }
            if(reading.isEngineCoolantLow() || reading.isCheckEngineLightOn()) {
                Alert alert = new Alert();
                alert.setVin(reading.getVin());
                alert.setPriority("LOW");
                alert.setDescription("Engine Coolant Low / Engine Light On");
                alert.setTimestamp(date);
                alert.setLatitude(reading.getLatitude());
                alert.setLongitude(reading.getLongitude());
                alertRepository.save(alert);
                System.out.println("Alert for Vehicle with VIN:- "+reading.getVin()+" with priority LOW");
            }
        }
        return repository.save(reading);
    }

}
