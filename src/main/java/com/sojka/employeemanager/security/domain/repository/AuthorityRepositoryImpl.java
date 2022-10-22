package com.sojka.employeemanager.security.domain.repository;

import com.sojka.employeemanager.security.domain.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorityRepositoryImpl implements AuthorityRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Authority> findAuthoritiesByUsername(String username) {
        String sql = "SELECT authority " +
                "FROM authorities " +
                "WHERE username = ? ";
        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Authority.class),
                username);
    }

    @Override
    public Optional<Authority> addUserAuthority(Authority authority) {
        return Optional.empty();
    }
}
