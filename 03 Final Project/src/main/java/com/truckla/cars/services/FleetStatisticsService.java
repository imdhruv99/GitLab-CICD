package com.truckla.cars.services;

import com.truckla.cars.model.Car;
import com.truckla.cars.model.FleetAge;
import com.truckla.cars.repositories.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;

@Service
public class FleetStatisticsService {

    @Autowired
    private transient CarsRepository repository;

    public FleetAge getAverageFleetAge() {

        List<Car> cars = repository.findAll();

        OptionalDouble average = cars
                .stream()
                .mapToDouble(a -> (2020 - a.getBuild()))
                .average();

        return new FleetAge(cars.size(), average.getAsDouble());
    }
}
