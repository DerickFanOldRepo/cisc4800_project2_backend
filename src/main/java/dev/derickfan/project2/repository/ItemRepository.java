package dev.derickfan.project2.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import dev.derickfan.project2.model.Item;

@Repository
public class ItemRepository {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemImageRepository itemImageRepository;

    @Autowired
    private JdbcTemplate jdbc;

    // Returns 1 if entry has been added
    // Returns 0 if category does not exist or item already exists
    public int addItem(String name, int categoryId, int itemImageId) {
        String sql = "INSERT INTO ITEMS VALUE (null, ?, ?, ?)";
        try {
            return jdbc.update(sql, name, categoryId, itemImageId);
        } catch (NullPointerException | DuplicateKeyException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Returns a list of all items
    public List<Item> getAllItems() {
        String sql = "SELECT * FROM ITEMS";
        return jdbc.query(sql, new ItemMapper());
    }

    // Returns a Item object with the given id
    public Item getItemById(int id) {
        String sql = "SELECT * FROM ITEMS WHERE ID = ?";
        return jdbc.queryForObject(sql, new ItemMapper(), id);
    }

    // Returns a Item object with the given id
    public Item getItemByName(String name) {
        String sql = "SELECT * FROM ITEMS WHERE NAME = ?";
        return jdbc.queryForObject(sql, new ItemMapper(), name);
    }

    // Returns 1 if it category has been updated
    // Returns 0 if entry does not exist or newCategory doesn't exist
    public int updateCategory(String name, int categoryId) {
        String sql = "UPDATE ITEMS SET CATEGORY_ID = ? WHERE NAME = ?";
        try {
            return jdbc.update(sql, categoryId, name);
        } catch (NullPointerException e) {
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

    private class ItemMapper implements RowMapper<Item> {

        @Override
        public Item mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Item item = new Item();
            item.setId(resultSet.getInt("ID"));
            item.setName(resultSet.getString("NAME"));
            item.setCategory(categoryRepository.getCategoryByID(resultSet.getInt("CATEGORY_ID")));
            item.setImageURL(itemImageRepository.getItemImageById(resultSet.getInt("ITEM_IMAGE_ID")));
            return item;
        }

    }

}