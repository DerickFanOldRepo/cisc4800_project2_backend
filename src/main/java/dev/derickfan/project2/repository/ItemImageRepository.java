package dev.derickfan.project2.repository;

import dev.derickfan.project2.model.ItemImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ItemImageRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int addItemImage(String url) {
        String sql = "INSERT INTO ITEM_IMAGES VALUES (null, ?)";

        return jdbcTemplate.update(sql, url);
    }

    public List<ItemImage> getAllItemImages() {
        String sql = "SELECT * FROM ITEM_IMAGES";

        return jdbcTemplate.query(sql, new ItemImageMapper());
    }

    public ItemImage getItemImageById(int itemImageId) {
        String sql = "SELECT * FROM ITEM_IMAGES WHERE ID = ?";

        return jdbcTemplate.queryForObject(sql, new ItemImageMapper(), itemImageId);
    }

    public ItemImage getItemImageByUrl(String url) {
        String sql = "SELECT * FROM ITEM_IMAGES WHERE URL = ?";

        return jdbcTemplate.queryForObject(sql, new ItemImageMapper(), url);
    }

    public int deleteItemImageById(int imageImageId) {
        String sql = "DELETE FROM ITEM_IMAGES WHERE ID = ?";
        return jdbcTemplate.update(sql, imageImageId);
    }

    private class ItemImageMapper implements RowMapper<ItemImage> {

        @Override
        public ItemImage mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            ItemImage itemImage = new ItemImage();
            itemImage.setUrl(resultSet.getString("URL"));
            itemImage.setId(resultSet.getInt("ID"));
            return itemImage;
        }

    }

}
