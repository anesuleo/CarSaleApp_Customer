package ie.atu.carsaleapp_customer.controllers;

import ie.atu.carsaleapp_customer.entity.Car;
import ie.atu.carsaleapp_customer.feignClient.CustomerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final CustomerClient customerClient;
    @Autowired
    public AdminController( CustomerClient customerClient) {
        this.customerClient = customerClient;

    }
    @PostMapping("/addCar")
    public Car addCar(@RequestBody Car car) {
        return customerClient.addCar(car);
    }
}
