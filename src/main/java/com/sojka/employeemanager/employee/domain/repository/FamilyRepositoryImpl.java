package com.sojka.employeemanager.employee.domain.repository;

import com.sojka.employeemanager.employee.domain.Family;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FamilyRepositoryImpl implements FamilyRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Family> findAllFamilyMembers(String id) {
        String sql = "SELECT * " +
                "FROM family " +
                "WHERE id = ?";
        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Family.class),
                id);
    }

    @Override
    public List<Family> findAllChildren(String id) {
        String sql = "SELECT * " +
                "FROM family " +
                "WHERE id = ? " +
                "AND kinship = 'child'";
        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Family.class),
                id);
    }

    @Override
    public Family save(Family familyMember) {
        String sql = "INSERT INTO family (id, first_name, second_name, last_name, birth_date, kinship) " +
                "VALUE (?, ?, ?, ?, ?, ? )";
        jdbcTemplate.update(sql,
                familyMember.getId(),
                familyMember.getFirstName(),
                familyMember.getSecondName(),
                familyMember.getLastName(),
                familyMember.getBirthDate(),
                familyMember.getKinship());
        return findFamilyMember(familyMember);
    }

    public Family findFamilyMember(Family familyMember) {
        String sql = "SELECT * " +
                "FROM family " +
                "WHERE id = ? " +
                "AND first_name = ? " +
                "AND second_name = ? " +
                "AND last_name = ? " +
                "AND birth_date = ? " +
                "AND kinship = ? ";
        return jdbcTemplate.queryForObject(sql,
                BeanPropertyRowMapper.newInstance(Family.class),
                familyMember.getId(),
                familyMember.getFirstName(),
                familyMember.getSecondName(),
                familyMember.getLastName(),
                familyMember.getBirthDate(),
                familyMember.getKinship());
    }
}
