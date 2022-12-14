package com.sojka.employeemanager.employee.domain.repository;

import com.sojka.employeemanager.employee.domain.Employee;
import com.sojka.employeemanager.employee.domain.exceptions.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Employee> findAllEmployees() {
        String sql = "SELECT * " +
                "FROM employees";
        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Employee.class));
    }

    @Override
    public Optional<Employee> findEmployeeById(String number) {
        String sql = "SELECT * " +
                "FROM employees " +
                "WHERE id = ?";
        List<Employee> employee = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Employee.class),
                number);
        return employee.isEmpty() ? Optional.empty() : Optional.of(employee.get(0));
    }

    @Override
    public Employee save(Employee employee) {
        String sql = "INSERT INTO employees (first_name, second_name, last_name, birth_date, personal_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, employee.getFirstName(),
                employee.getSecondName(),
                employee.getLastName(),
                employee.getBirthDate(),
                employee.getPersonalId());
        return findEmployeeByPersonalId(employee.getPersonalId())
                .orElseThrow(() -> new EmployeeNotFoundException(employee.getId()));
    }

    @Override
    public Optional<Employee> findEmployeeByPersonalId(String personalId) {
        String sql = "SELECT * " +
                "FROM employees " +
                "WHERE personal_id = ?";
        List<Employee> employee = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Employee.class),
                personalId);
        return employee.isEmpty() ? Optional.empty() : Optional.of(employee.get(0));
    }

    @Override
    public List<Employee> saveAll(List<Employee> employees) {
        List<Employee> saved = employees.stream()
                .filter(this::exists)
                .collect(Collectors.toList());
        saved
                .forEach(this::save);
        return saved;
    }

    @Override
    public boolean exists(Employee employee) {
        String sql = "SELECT IF( EXISTS(" +
                "SELECT * FROM employees " +
                "WHERE personal_id = ?), " +
                "true, false) AS boolean";
        return 1 == jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> rs.getInt("boolean"),
                employee.getPersonalId());
    }

    @Override
    public void delete(String personalId) {
        String sql = "DELETE FROM employees " +
                "WHERE personal_id = ? ";
        jdbcTemplate.update(sql, personalId);
    }
}
