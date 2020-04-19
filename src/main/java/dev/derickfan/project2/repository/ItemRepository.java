package dev.derickfan.project2.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import dev.derickfan.project2.model.Category;
import dev.derickfan.project2.model.Item;

@Repository
public class ItemRepository {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private JdbcTemplate jdbc;

    // Returns 1 if entry has been added
    // Returns 0 if category does not exist or item already exists
    public int addItem(String name, String categoryName) {
        Category category = categoryRepository.getCategoryByName(categoryName);
        String sql = "INSERT INTO ITEMS VALUE (null, ?, ?)";
        try {
            return jdbc.update(sql, name, category.getId());
        } catch (NullPointerException | DuplicateKeyException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Returns 1 if entry has been deleted
    // Returns 0 if entry does not exist
    public int deleteItem(String name) {
        String sql = "DELETE FROM ITEMS WHERE NAME = ?";
        return jdbc.update(sql, name);
    }

    // Returns 1 if it category has been updated
    // Returns 0 if entry does not exist or newCategory doesn't exist
    public int updateCategory(String name, String newCategoryName) {
        Category category = categoryRepository.getCategoryByName(newCategoryName);
        String sql = "UPDATE ITEMS SET CATEGORY_ID = ? WHERE NAME = ?";
        try {
            return jdbc.update(sql, category.getId(), name);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Returns a list of all items
    public List<Item> getAllItems() {
        String sql = "SELECT * FROM ITEMS";
        return jdbc.query(sql, new ItemMapper());
    }

    private class ItemMapper implements RowMapper<Item> {

        @Override
        public Item mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Item item = new Item();
            item.setId(resultSet.getInt("ID"));
            item.setName(resultSet.getString("NAME"));
            item.setCategory(categoryRepository.getCategoryByID(resultSet.getInt("CATEGORY_ID")));
            return item;
        }
        
    }

}