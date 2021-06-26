package com.truckla.cars.repositories;

import com.truckla.cars.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarsRepository extends JpaRepository<Car, Long> {
}