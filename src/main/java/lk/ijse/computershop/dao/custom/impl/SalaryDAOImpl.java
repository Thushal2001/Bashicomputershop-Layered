package lk.ijse.computershop.dao.custom.impl;

import lk.ijse.computershop.dao.custom.SalaryDAO;
import lk.ijse.computershop.dao.custom.impl.util.SQLUtil;
import lk.ijse.computershop.entity.Salary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryDAOImpl implements SalaryDAO {

    @Override
    public List<Salary> loadAll() throws SQLException {
        List<Salary> salaryList = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM salary");
        while (rst.next()) {
            Salary salary = new Salary(rst.getString(1), rst.getString(2), rst.getDouble(3), rst.getDate(4).toLocalDate());
            salaryList.add(salary);
        }
        return salaryList;
    }

    @Override
    public int save(Salary s) throws SQLException {
        return SQLUtil.execute("INSERT INTO salary VALUES(?, ?, ?, ?)",s.getCode(),s.getEmployeeId(),s.getAmount(),s.getDate());
    }

    @Override
    public Salary search(String id) throws SQLException {
        return null;
    }

    @Override
    public int update(Salary entity) throws SQLException {
        return 0;
    }

    @Override
    public int delete(String id) throws SQLException {
        return 0;
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT code FROM salary ORDER BY code DESC LIMIT 1");
        if (rst.next()) {
            return splitSalaryCode(rst.getString(1));
        }
        return splitSalaryCode(null);
    }

    private String splitSalaryCode(String currentId) {
        if (currentId != null) {
            String[] strings = currentId.split("S");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "S" + String.format("%02d", id);
        }
        return "S01";
    }
}
