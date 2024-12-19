package ie.atu.carsaleapp_customer.repository;

import ie.atu.carsaleapp_customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //Optional<Customer> findByEmail(java.lang.String email);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhoneNo(String phoneNo);
}
