package dev.derickfan.project2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    
    @Autowired
    private JdbcTemplate jdbc;
    
    // Inserts a new user into the sql database
    public int save(String name, String email, String password) {
        String sql = "INSERT INTO USERS VALUES (null, ?, ?, ?)";
        return jdbc.update(sql, name, email, password);
    }
    
    // Updates the user's password after confirming old password
    public int updatePassword(String name, String oldPassword, String newPassword) {
        // Implement confirming old password
        String sql = "UPDATE USERS SET PASSWORD = ? WHERE NAME = ?";
        return jdbc.update(sql, newPassword, name);
    }

    // Updates the user's email after confirming old email
    public int updateEmail(String name, String oldEmail, String newEmail) {
        // Implement confirming old email
        String sql = "UPDATE USERS SET EMAIL = ? WHERE NAME = ?";
        return jdbc.update(sql, oldEmail, newEmail);
    }

    // Deletes the user entry by id
    public int deleteById(int id) {
        String sql = "DELETE USERS WHERE ID = ?";
        
        return jdbc.update(sql, id);
    }
    
    // Deletes the user entry by name
    public int deleteByUsername(String name) {
        String sql = "DELETE USERS WHERE NAME = ?";
        return jdbc.update(sql, name);
    }
    
    // Returns all the user entries in the table
    public List<User> findAll() {
        String sql = "SELECT * FROM USERS";
        return jdbc.query(sql, new UserMapper());
    }
    
    // Returns a user by the username
    public User find(String name) {
        String sql = "SELECT * FROM USERS WHERE NAME = ?";
        return jdbc.queryForObject(sql, new Object[]{name}, new UserMapper());
    }
    
    private static final class UserMapper implements RowMapper<User> {
        
        @Override
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            
            return user;
        }
        
    }
    
}