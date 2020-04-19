package dev.derickfan.project2.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import dev.derickfan.project2.model.Item;

@Repository
public class ItemRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public int addItem(String name, String img, int category_id) {
        String sql = "INSERT INTO ITEMS VALUE (null, ?, ?, ?)";
        return jdbc.update(sql, name, img, category_id);
    }

    public int deleteItem(String name) {
        String sql = "DELETE FROM ITEMS WHERE NAME = ?";
        return jdbc.update(sql, name);
    }

    public int updateCategory(String name, String newCategory) {
        String sql = "UPDATE ITEMS SET CATEGORY = ? WHERE NAME = ?";
        return jdbc.update(sql, newCategory, name);
    }

    public List<Item> getAllItems() {
        String sql = "SELECT * FROM ITEMS";
        return jdbc.query(sql, new ItemMapper());
    }

    private static final class ItemMapper implements RowMapper<Item> {

        private CategoryRepository categoryRepository = new CategoryRepository();

        @Override
        public Item mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Item item = new Item();
            item.setId(resultSet.getInt("ID"));
            item.setName(resultSet.getString("NAME"));
            item.setImage(resultSet.getString("IMAGE"));
            item.setCategory(categoryRepository.getCategory(resultSet.getInt("CATEGORY_ID")));
            return item;
        }
        
    }

}