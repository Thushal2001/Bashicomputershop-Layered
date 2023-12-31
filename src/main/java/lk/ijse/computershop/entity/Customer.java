package lk.ijse.computershop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {
    private String id;
    private String name;
    private String nic;
    private String email;
    private String contact;
    private String address;
}
