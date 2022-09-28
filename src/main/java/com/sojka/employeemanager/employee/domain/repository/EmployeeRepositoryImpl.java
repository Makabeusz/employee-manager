package com.sojka.employeemanager.employee.domain.repository;

import com.sojka.employeemanager.employee.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Employee> findAllEmployees() {
        String sql = "SELECT * FROM employees";
        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Employee.class));
    }

    @Override
    public Optional<Employee> findEmployeeById(String number) {
        String sql = "SELECT * FROM employees\n" +
                "WHERE id=?";
        Employee employee = jdbcTemplate.queryForObject(sql,
                BeanPropertyRowMapper.newInstance(Employee.class),
                number);
        return Optional.ofNullable(employee);
    }

    @Override
    public Employee save(Employee employee) {
        String sql = "INSERT INTO employees (first_name, second_name, last_name, birth_date, personal_id)\n" +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, employee.getFirstName(),
                employee.getSecondName(),
                employee.getLastName(),
                employee.getBirthDate(),
                employee.getPersonalId());
        return findEmployeeByPersonalId(employee.getPersonalId()).get();
    }

    @Override
    public Optional<Employee> findEmployeeByPersonalId(String personalId) {
        String sql = "SELECT * FROM employees\n" +
                "WHERE personal_id=?";
        List<Employee> employee = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Employee.class),
                personalId);
        return employee.isEmpty() ? Optional.empty() : Optional.of(employee.get(0));
    }
}
