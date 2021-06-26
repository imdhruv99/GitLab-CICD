package com.truckla.cars.services;

import com.truckla.cars.model.Car;
import com.truckla.cars.repositories.CarsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class FleetStatisticsServiceTest {

    @InjectMocks
    private transient FleetStatisticsService service;

    @Mock
    private transient CarsRepository repo;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void oneCar() {
        List<Car> myList = new ArrayList<>();
        Car car = new Car(1L, "Ford", "T", 1900);
        myList.add(car);

        when(repo.findAll()).thenReturn(myList);

        assertEquals(service.getAverageFleetAge().getAge(), 120, 0);
    }

    @Test
    public void twoCars() {
        List<Car> myList = new ArrayList<>();
        myList.add(new Car(1L, "Dacia", "Duster", 2000));
        myList.add(new Car(2L, "Dacia", "Logan", 2010));

        when(repo.findAll()).thenReturn(myList);

        assertEquals(service.getAverageFleetAge().getAge(), 15, 0);
    }
}
