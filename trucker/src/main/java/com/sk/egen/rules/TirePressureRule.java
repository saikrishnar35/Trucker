package com.sk.egen.rules;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sk.egen.entity.Alert;
import com.sk.egen.entity.Reading;
import com.sk.egen.entity.Vehicle;
import com.sk.egen.repository.AlertRepository;
import com.sk.egen.repository.VehicleRepository;

@Rule(name = "Low / High tire pressure", description = "If any tire pressure <32 / tire pressure>36")
@Component
public class TirePressureRule {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Condition
    public boolean condition(@Fact("tirepressure") Reading reading) {
        boolean cond = false;

        try {
            Optional<Vehicle> existing = vehicleRepository.findByVin(reading.getVin());
            cond = existing.filter(vehicle -> reading.getTires().getFrontLeft() < 32 || reading.getTires().getFrontLeft() > 36 || reading.getTires().getFrontRight() > 36 || reading.getTires().getFrontRight() < 32 || reading.getTires().getRearLeft() > 36 || reading.getTires().getRearLeft() < 32 || reading.getTires().getRearRight() > 36 || reading.getTires().getRearRight() < 32).isPresent();
        }
        catch(NullPointerException e) {
            e.getMessage();
        }
        return cond;
    }

    @Action
    public void action(@Fact("tirepressure") Reading reading) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss.s");
        Alert alert = new Alert();
        alert.setVin(reading.getVin());
        alert.setPriority("LOW");
        alert.setDescription("High / Low  Tire Pressure");
        alert.setTimestamp(date);
        alertRepository.save(alert);
    }
}
