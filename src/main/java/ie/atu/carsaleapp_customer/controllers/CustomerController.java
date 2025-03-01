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
import java.util.NoSuchElementException;
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
        try {
            Optional<Customer> existingCustomerByEmail = customerService.findByEmail(customer.getEmail());
            if (existingCustomerByEmail.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("success", false, "message", "Email already exists"));
            }
            Optional<Customer> existingCustomerByPhone = customerService.findByPhoneNo(customer.getPhoneNo());
            if (existingCustomerByPhone.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("success", false, "message", "Phone number already exists"));
            }
            Customer newCustomer = customerService.addCustomer(customer);
            return ResponseEntity.ok(Map.of("success", true, "customer", newCustomer));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", "Registration failed: " + e.getMessage()));
        }
    }
    @PutMapping("/updatePassword/{email}")
    public ResponseEntity<String> updatePassword(@PathVariable String email, @RequestBody Map<String, String> requestBody) {
        String newPassword = requestBody.get("password");
        if (newPassword == null || newPassword.isBlank()) {
            return ResponseEntity.badRequest().body("Password is required.");
        }

        boolean isUpdated = customerService.updatePassword(email, newPassword);
        if (isUpdated) {
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
        }
    }

    @DeleteMapping("/deleteCustomer/{email}")
    public void deleteCustomerByEmail(@PathVariable String email) {
        customerService.deleteCustomerByEmail(email);
        System.out.println("Customer deleted successfully.");
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