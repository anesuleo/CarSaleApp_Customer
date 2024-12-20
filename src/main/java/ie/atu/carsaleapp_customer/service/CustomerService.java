package ie.atu.carsaleapp_customer.service;

import ie.atu.carsaleapp_customer.repository.CustomerRepository;
import ie.atu.carsaleapp_customer.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer addCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

//    public Optional<Customer> findByEmail(String email) {
//        return customerRepository.findByEmail(email);
//    }
public boolean updatePassword(String email, String newPassword) {
    Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
    if (optionalCustomer.isPresent()) {
        Customer customer = optionalCustomer.get();
        customer.setPassword(newPassword);
        customerRepository.save(customer);
        return true;
    }
    return false;
}
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
    public Optional<Customer> findByPhoneNo(String phoneNo) {
        return customerRepository.findByPhoneNo(phoneNo);
    }

}
