package lk.ijse.computershop.bo.custom;

import lk.ijse.computershop.bo.SuperBO;
import lk.ijse.computershop.dto.EmployeeDTO;
import lk.ijse.computershop.dto.SalaryDTO;

import java.sql.SQLException;
import java.util.List;

public interface SalaryBO extends SuperBO {

    int saveSalary(SalaryDTO s) throws SQLException;

    List<SalaryDTO> loadAllSalary() throws SQLException;

    String generateNextSalaryCode() throws SQLException;

    List<String> loadEmployeeIds() throws SQLException;

    EmployeeDTO searchByEmployeeId(String employeeId) throws SQLException;
}
