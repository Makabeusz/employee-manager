package com.sojka.employeemanager.employee.domain.repository;

import com.sojka.employeemanager.employee.domain.Education;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class EducationRepositoryImpl implements EducationRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Education> findAllDegrees(String id) {
        String sql = "SELECT * " +
                "FROM education " +
                "WHERE id = ?";
        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Education.class),
                id);
    }

    @Override
    public Optional<Education> findMostRecentDegree(String id) {
        String sql = "SELECT * " +
                "FROM education " +
                "WHERE id = ? " +
                "ORDER BY finish_date DESC";
        List<Education> result = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Education.class),
                id);
        return Optional.ofNullable(result.get(0));
    }
}
