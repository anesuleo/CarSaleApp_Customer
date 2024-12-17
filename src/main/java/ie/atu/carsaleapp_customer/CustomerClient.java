package ie.atu.carsaleapp_customer;



import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

@FeignClient(name="car",url ="http://localhost:8080")
public interface CustomerClient {
    @GetMapping("cars/allcars")
     List<Car> getAllCars() ;
    @GetMapping("/cars/{id}")
    Car getCarById(@PathVariable("id") Long id);
}
