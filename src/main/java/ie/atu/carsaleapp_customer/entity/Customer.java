package ie.atu.carsaleapp_customer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customer_id;

    @Column(name = "first_name")
    @NotBlank(message = "firstName cannot be blank")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "lastName cannot be blank")
    private String lastName;

    @Column(name = "phone_number")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phoneNo;

    @Column(name = "email")
    @Email(message = "put valid email")
    private String email;

    @Column(name="password")
    @NotBlank(message = "Password can not be blank")
    private String password;

}
