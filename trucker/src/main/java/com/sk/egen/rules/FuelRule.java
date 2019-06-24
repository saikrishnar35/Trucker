package com.sk.egen.rules;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.core.BasicRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sk.egen.entity.Alert;
import com.sk.egen.entity.Reading;
import com.sk.egen.entity.Vehicle;
import com.sk.egen.repository.AlertRepository;
import com.sk.egen.repository.VehicleRepository;

@Rule(name = "Low Fuel Rule", description = "If fuel < 10%Max then fire an alert")
@Component
public class FuelRule extends BasicRule {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Condition
    public boolean lowFuel(@Fact("fuel") Reading reading) {

        Optional<Vehicle> existing = vehicleRepository.findByVin(reading.getVin());
        return existing.filter(vehicle -> reading.getFuelVolume() < 0.1 * vehicle.getMaxFuelVolume()).isPresent();
    }

    @Action
    public void lowFuelAlert(@Fact("fuel") Reading reading) {
        Date date = new Date(reading.getTimestamp());
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss.s");
        Alert alert = new Alert();
        alert.setVin(reading.getVin());
        alert.setPriority("MEDIUM");
        alert.setDescription("Low Fuel");
        alert.setTimestamp(new Date(df.format(date)));
        alertRepository.save(alert);
    }

}
