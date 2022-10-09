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
        if (result.isEmpty()) return Optional.empty();
        return Optional.of(result.get(0));
    }

    @Override
    public Education save(Education education) {
        String sql = "INSERT INTO education(id, degree, school_name, address, start_date, finish_date) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, education.getId(),
                education.getDegree(),
                education.getSchoolName(),
                education.getAddress(),
                education.getStartDate(),
                education.getFinishDate());
        return findDegree(education);
    }

    @Override
    public boolean exists(Education education) {
        String sql = "SELECT CASE WHEN EXISTS (SELECT * " +
                "FROM education " +
                "WHERE id = ? " +
                "AND degree = ? " +
                "AND school_name = ? " +
                "AND address = ? " +
                "AND start_date = ? " +
                "AND finish_date = ?) " +
                "THEN true " +
                "ELSE false END AS 'boolean'";
        return 1 == jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> rs.getInt("boolean"),
                education.getId(),
                education.getDegree(),
                education.getSchoolName(),
                education.getAddress(),
                education.getStartDate(),
                education.getFinishDate());
    }

    @Override
    public void delete(Education education) {
        String sql = "DELETE FROM education " +
                "WHERE id = ? " +
                "AND degree = ? " +
                "AND school_name = ? " +
                "AND address = ? " +
                "AND start_date = ? " +
                "AND finish_date = ? ";
        jdbcTemplate.update(sql,
                education.getId(),
                education.getDegree(),
                education.getSchoolName(),
                education.getAddress(),
                education.getStartDate(),
                education.getFinishDate());
    }

    private Education findDegree(Education education) {
        String sql = "SELECT * " +
                "FROM education " +
                "WHERE id = ? " +
                "AND degree = ? " +
                "AND school_name = ? " +
                "AND address = ? " +
                "AND start_date = ? " +
                "AND finish_date = ?";
        return jdbcTemplate.queryForObject(sql,
                BeanPropertyRowMapper.newInstance(Education.class),
                education.getId(),
                education.getDegree(),
                education.getSchoolName(),
                education.getAddress(),
                education.getStartDate(),
                education.getFinishDate());
    }
}
