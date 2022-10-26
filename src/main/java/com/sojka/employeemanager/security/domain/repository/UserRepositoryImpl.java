package com.sojka.employeemanager.security.domain.repository;

import com.sojka.employeemanager.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                "WHERE username = ? ";
        List<User> user = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(User.class),
                username);
        return user.isEmpty() ? Optional.empty() : Optional.of(user.get(0));
    }

    @Override
    public User createNewUser(User user) {
        String sql = "INSERT INTO users (username, email, password, enabled)" +
                "VALUE (?, ?, ?, ?) ";
        jdbcTemplate.update(sql,
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.isEnabled());
        return findUserByUsername(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User " + user.getUsername() + " have not been created."));
    }

    @Override
    public boolean exists(String username) {
        String sql = "SELECT IF( EXISTS( " +
                "SELECT * " +
                "FROM users " +
                "WHERE username = ? ), " +
                "true, false) AS boolean";
        return 1 == jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> rs.getInt("boolean"),
                username);
    }

    @Override
    public void deleteUserByUsername(String username) {
        String sql = "DELETE FROM users " +
                "WHERE username = ? ";
        jdbcTemplate.update(sql,
                username);
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT IF( EXISTS(" +
                "SELECT * " +
                "FROM users " +
                "WHERE email = ? ), " +
                "true, false) AS boolean";
        return 1 == jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> rs.getInt("boolean"),
                email);
    }
}
