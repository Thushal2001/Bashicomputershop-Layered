package lk.ijse.computershop.bo.custom.impl;

import lk.ijse.computershop.bo.custom.EmployeeBO;
import lk.ijse.computershop.dao.DAOFactory;
import lk.ijse.computershop.dao.custom.EmployeeDAO;
import lk.ijse.computershop.dto.EmployeeDTO;
import lk.ijse.computershop.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {

    private EmployeeDAO employeeDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public List<EmployeeDTO> loadAllEmployees() throws SQLException {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        List<Employee> employees = employeeDAO.loadAll();
        for (Employee e : employees) {
            employeeDTOList.add(new EmployeeDTO(e.getId(), e.getName(), e.getContact(), e.getJobRole(), e.getUsername(), e.getPassword()));
        }
        return employeeDTOList;
    }

    @Override
    public int saveEmployee(EmployeeDTO e) throws SQLException {
        return employeeDAO.save(new Employee(e.getId(), e.getName(), e.getContact(), e.getJobRole(), e.getUsername(), e.getPassword()));
    }

    @Override
    public EmployeeDTO searchEmployee(String id) throws SQLException {
        Employee e = employeeDAO.search(id);
        return new EmployeeDTO(e.getId(), e.getName(), e.getContact(), e.getJobRole(), e.getUsername(), e.getPassword());
    }

    @Override
    public int updateEmployee(EmployeeDTO e) throws SQLException {
        return employeeDAO.update(new Employee(e.getId(), e.getName(), e.getContact(), e.getJobRole(), e.getUsername(), e.getPassword()));
    }

    @Override
    public int deletEmployeee(String id) throws SQLException {
        return employeeDAO.delete(id);
    }

    @Override
    public String generateNextEmployeeId() throws SQLException {
        return employeeDAO.generateNextId();
    }
}
