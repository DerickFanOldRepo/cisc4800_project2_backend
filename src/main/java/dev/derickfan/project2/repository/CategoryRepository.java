package dev.derickfan.project2.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import dev.derickfan.project2.model.Category;

@Repository
public class CategoryRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public int addCategory(String name) {
        String sql = "INSERT INTO CATEGORIES VALUES (null, ?)";
        return jdbc.update(sql, name);
    }

    public int deleteCategory(String name) {
        String sql = "DELETE FROM CATEGORIES WHERE NAME = ?";
        return jdbc.update(sql, name);
    }

    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM CATEGORIES";
        return jdbc.query(sql, new CategoryMapper());
    }

    public Category getCategory(int category_id) {
        String sql = "SELECT * FROM CATEGORIES WHERE ID = ?";
        return jdbc.queryForObject(sql, new Object[]{category_id}, new CategoryMapper());
    }

    private static final class CategoryMapper implements RowMapper<Category> {
        
        @Override
        public Category mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Category category = new Category();
            category.setId(resultSet.getInt("ID"));
            category.setName(resultSet.getString("NAME"));
            return category;
        }

    }

}