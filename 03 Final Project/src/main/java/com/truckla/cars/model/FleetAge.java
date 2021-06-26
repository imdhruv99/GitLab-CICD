package com.truckla.cars.model;

public class FleetAge {
    private int vehicles;
    private double age;

    public FleetAge(int vehicles, double age) {
        this.vehicles = vehicles;
        this.age = age;
    }

    public int getVehicles() {
        return vehicles;
    }

    public double getAge() {
        return age;
    }

    public void setVehicles(int vehicles) {
        this.vehicles = vehicles;
    }

    public void setAge(double age) {
        this.age = age;
    }
}
