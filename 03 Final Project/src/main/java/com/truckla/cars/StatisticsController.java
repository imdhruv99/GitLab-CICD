package com.truckla.cars;

import com.truckla.cars.model.FleetAge;
import com.truckla.cars.services.FleetStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("statistics")
public class StatisticsController {

    @Autowired
    transient FleetStatisticsService service;

    @GetMapping(value = "/{age}")
    public FleetAge getCarById() {
        return service.getAverageFleetAge();
    }
}
