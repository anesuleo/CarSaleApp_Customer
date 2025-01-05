package ie.atu.carsaleapp_customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.atu.carsaleapp_customer.controllers.CustomerController;
import ie.atu.carsaleapp_customer.entity.Car;
import ie.atu.carsaleapp_customer.entity.Customer;
import ie.atu.carsaleapp_customer.feignClient.CustomerClient;
import ie.atu.carsaleapp_customer.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerClient customerClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddCustomer() throws Exception {
        Customer customer = new Customer(1, "John", "Doe", "1234567890", "john@example.com", "password123");
        when(customerService.addCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/customer/addCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void testGetAllCustomers() throws Exception {
        List<Customer> customers = List.of(
                new Customer(1, "John", "Doe", "1234567890", "john@example.com", "password123"),
                new Customer(2, "Jane", "Smith", "9876543210", "jane@example.com", "mypassword")
        );
        when(customerService.getAllCustomer()).thenReturn(customers);


        mockMvc.perform(get("/customer/allCustomers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].email").value("jane@example.com"));
    }

    @Test
    void testCustomerLogin() throws Exception {

        Customer customer = new Customer(1, "Jane", "Doe", "1234567890", "jane@example.com", "password123");
        when(customerService.findByEmail("jane@example.com")).thenReturn(Optional.of(customer));

        Map<String, String> loginRequest = Map.of("email", "jane@example.com", "password", "password123");

        mockMvc.perform(post("/customer/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Login successful"));
    }

}
