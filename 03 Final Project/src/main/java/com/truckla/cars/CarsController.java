package com.truckla.cars;

import com.truckla.cars.model.Car;
import com.truckla.cars.repositories.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cars")
public class CarsController {

    @Autowired
    private transient CarsRepository repository;

    @GetMapping
    public List<Car> getCars() {
        return repository.findAll();
    }

    @PostMapping
    public Car addCar(@RequestBody Car car) {
        return repository.save(car);
    }

    @GetMapping(value = "/{id}")
    public Car getCarById(@PathVariable("id") long id) {
        return repository.getOne(id);
    }

    @DeleteMapping(value = "/{id}")
    public void removeCarById(@PathVariable("id") long id) {
        repository.deleteById(id);
    }
}
