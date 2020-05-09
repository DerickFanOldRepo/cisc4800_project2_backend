package dev.derickfan.project2.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dev.derickfan.project2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    
    @Autowired
    private JdbcTemplate jdbc;
    
    // Inserts a new user into the sql database
    public int addUser(String username, String email, String password) {
        String sql = "INSERT INTO USERS VALUES (null, ?, ?, ?)";
        return jdbc.update(sql, username, email, password);
    }

    // Returns all the user entries in the table
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM USERS";
        return jdbc.query(sql, new UserMapper());
    }

    // Returns a user by the username
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
        return jdbc.queryForObject(sql, new Object[]{username}, new UserMapper());
    }

    // Returns a user by the id
    public User getUserById(int id) {
        String sql = "SELECT * FROM USERS WHERE ID = ?";
        return jdbc.queryForObject(sql, new UserMapper(), id);
    }
    
    // Updates the user's password after confirming old password
    public int updatePassword(String username, String oldPassword, String newPassword) {
        // Implement confirming old password
        String sql = "UPDATE USERS SET PASSWORD = ? WHERE USERNAME = ?";
        return jdbc.update(sql, newPassword, username);
    }

    // Updates the user's email after confirming old email
    public int updateEmail(String username, String oldEmail, String newEmail) {
        // Implement confirming old email
        String sql = "UPDATE USERS SET EMAIL = ? WHERE USERNAME = ?";
        return jdbc.update(sql, newEmail, username);
    }

    // Deletes the user entry by id
    public int deleteById(int id) {
        String sql = "DELETE FROM USERS WHERE ID = ?";
        
        return jdbc.update(sql, id);
    }
    
    // Deletes the user entry by name
    public int deleteByUsername(String username) {
        String sql = "DELETE FROM USERS WHERE USERNAME = ?";
        return jdbc.update(sql, username);
    }

    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
        return jdbc.queryForObject(sql, new UserMapper(), username, password);
    }

    public User signup(String username, String email, String password) {
        this.addUser(username, email, password);
        return this.getUserByUsername(username);
    }

    private final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt("ID"));
            user.setUsername(resultSet.getString("USERNAME"));
            user.setEmail(resultSet.getString("EMAIL"));
            user.setPassword(resultSet.getString("PASSWORD"));
            return user;
        }

    }

}