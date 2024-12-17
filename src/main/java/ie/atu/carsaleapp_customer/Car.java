package ie.atu.carsaleapp_customer;

import lombok.Data;

@Data
public class Car {

    private int car_id;
    private String make;
    private String model;
    private int year;
    private double cost;
}
