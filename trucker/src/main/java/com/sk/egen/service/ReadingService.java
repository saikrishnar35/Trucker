package com.sk.egen.service;

import java.util.List;

import com.sk.egen.entity.Reading;

public interface ReadingService {

    List<Reading> findAll();

    Reading findOne(String id);

    Reading create(Reading reading);

}
