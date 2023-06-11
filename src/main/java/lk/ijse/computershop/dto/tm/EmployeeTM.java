package lk.ijse.computershop.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class EmployeeTM {
    private String id;
    private String name;
    private String contact;
    private String jobRole;
    private String username;
    private String password;
}
