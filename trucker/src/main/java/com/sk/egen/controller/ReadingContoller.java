package com.sk.egen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rg.egen.entity.Reading;
import com.rg.egen.service.ReadingService;

@RestController
@RequestMapping("/readings")
@CrossOrigin(origins = {"http://mocker.egen.io", "http://mocker.ennate.academy"})
public class ReadingContoller {

    private final ReadingService service;

    @Autowired
    public ReadingContoller(ReadingService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Reading> findAll() {
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Reading create(@RequestBody Reading reading) {
        System.out.println("Creating Reading record");
        return service.create(reading);
    }
}
