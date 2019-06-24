package com.sk.egen.service;

import java.util.List;

import com.sk.egen.entity.Alert;

public interface AlertService {

    List<Alert> findAlertsByPriority(String priority);

    List<Alert> findAlertsByVin(String vin);

    Alert create(Alert alert);
}
