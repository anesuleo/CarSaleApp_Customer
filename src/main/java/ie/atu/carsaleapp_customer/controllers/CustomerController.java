package ie.atu.carsaleapp_customer.controllers;

import ie.atu.carsaleapp_customer.entity.Car;
import ie.atu.carsaleapp_customer.entity.Customer;
import ie.atu.carsaleapp_customer.feignClient.CustomerClient;
import ie.atu.carsaleapp_customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerClient customerClient;
    @Autowired
    public CustomerController(CustomerService customerService, CustomerClient customerClient) {
        this.customerService = customerService;
        this.customerClient = customerClient;
    }
    @PostMapping("/addCustomer")
    public Customer addCustomer(@Valid @RequestBody Customer customer){
        return customerService.addCustomer(customer);
    }

    @GetMapping("/allCustomers")
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomer();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Customer loginRequest) {
        Optional<Customer> customer = customerService.findByEmail(loginRequest.getEmail());
        if (customer.isPresent() && customer.get().getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Login successful"));
        } else if (customer.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Incorrect password"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "No account found. Please register."));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody Customer customer) {
        if (customerService.findByEmail(customer.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("success", false, "message", "Email already registered."));
        }
        Customer newCustomer = customerService.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success", true, "message", "Registration successful", "customer", newCustomer));
    }
    @GetMapping("/allcars")
    public List<Car> getAllCarsFromCarService() {
      return customerClient.getAllCars();
    }
    @GetMapping("/car/{id}")
    public Car getCarById(@PathVariable Long id){
        return customerClient.getCarById(id);
    }
}