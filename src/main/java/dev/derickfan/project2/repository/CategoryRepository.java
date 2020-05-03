package dev.derickfan.project2.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import dev.derickfan.project2.model.Category;

@Repository
public class CategoryRepository {

    @Autowired
    private JdbcTemplate jdbc;

    // Returns 1 if category has been added to the table
    // Returns 0 if category already exists
    public int addCategory(String name) {
        String sql = "INSERT INTO CATEGORIES VALUES (null, ?)";
        try {
            return jdbc.update(sql, name);
        } catch (DuplicateKeyException e) {
            return 0;
        }
    }

    // Returns 1 if the entry has been deleted
    // Returns 0 if category does not exist
    public int deleteCategory(String name) {
        String sql = "DELETE FROM CATEGORIES WHERE NAME = ?";
        return jdbc.update(sql, name);
    }

    // Returns a list of all categories
    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM CATEGORIES";
        return jdbc.query(sql, new CategoryMapper());
    }

    // Returns the category by the name
    // Returns null if it does not exist
    public Category getCategoryByName(String name) {
        String sql = "SELECT * FROM CATEGORIES WHERE NAME = ?";
        try {
            return jdbc.queryForObject(sql, new Object[]{name}, new CategoryMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Returns the category by the id
    // Returns null if it does not exist
    public Category getCategoryByID(int category_id) {
        String sql = "SELECT * FROM CATEGORIES WHERE ID = ?";
        try {
            return jdbc.queryForObject(sql, new CategoryMapper(), category_id);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    private final class CategoryMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Category category = new Category();
            category.setId(resultSet.getInt("ID"));
            category.setName(resultSet.getString("NAME"));
            return category;
        }

    }

}