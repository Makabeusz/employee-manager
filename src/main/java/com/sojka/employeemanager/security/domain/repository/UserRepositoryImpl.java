package com.sojka.employeemanager.security.domain.repository;

import com.sojka.employeemanager.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        String sql = "SELECT * " +
                "FROM users " +
                "WHERE username = ?";
        List<User> user = jdbcTemplate.query(sql
                , BeanPropertyRowMapper.newInstance(User.class),
                username);
        return user.isEmpty() ? Optional.empty() : Optional.of(user.get(0));
    }
}
