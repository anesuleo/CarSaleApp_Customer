package ie.atu.carsaleapp_customer.feignClient;



import ie.atu.carsaleapp_customer.entity.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

@FeignClient(name="car",url ="http://localhost:8080/cars")
public interface CustomerClient {
    @GetMapping("/allcars")
     List<Car> getAllCars() ;

    @PostMapping("/addCar")
    Car addCar(@RequestBody Car car);
}
