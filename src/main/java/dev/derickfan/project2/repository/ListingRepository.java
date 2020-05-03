package dev.derickfan.project2.repository;

import dev.derickfan.project2.model.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ListingRepository {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JdbcTemplate jdbc;

    public int addListing(int userId, int itemId, double price, int quantity) {
        String sql = "INSERT INTO LISTINGS (USER_ID, ITEM_ID, PRICE, QUANTITY) VALUES (?, ?, ?, ?)";

        return jdbc.update(sql, userId, itemId, price, quantity);

    }

    public int updateListingQuantity(int userId, int itemId, int quantity) {
        String sql = "UPDATE LISTINGS SET QUANTITY = ? WHERE USER_ID = ? AND ITEM_ID = ?";

        return jdbc.update(sql, quantity, userId, itemId);
    }

    public int updateListingPrice(int userId, int itemId, double price) {
        String sql = "UPDATE LISTINGS SET PRICE = ? WHERE USER_ID = ? AND ITEM_ID = ?";

        return jdbc.update(sql, price, userId, itemId);
    }

    public List<Listing> getAllListings() {
        String sql = "SELECT * FROM LISTINGS";

        return jdbc.query(sql, new ListingMapper());
    }

    public List<Listing> getListingsByUserId(int userId) {
        String sql = "SELECT * FROM LISTINGS WHERE USER_ID = ?";

        return jdbc.query(sql, new ListingMapper(), userId);
    }

    public List<Listing> getListingsByItemId(int itemId) {
        String sql = "SELECT * FROM LISTINGS WHERE ITEM_ID = ?";

        return jdbc.query(sql, new ListingMapper(), itemId);
    }

    public int deleteListing(int userId, int itemId) {
        String sql = "DELETE FROM LISTINGS WHERE USER_ID = ? AND ITEM_ID = ?";

        return jdbc.update(sql, userId, itemId);
    }

    private final class ListingMapper implements RowMapper<Listing> {

        @Override
        public Listing mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Listing listing = new Listing();
            listing.setId(resultSet.getInt("ID"));
            listing.setItem(itemRepository.getItemById(resultSet.getInt("ITEM_ID")));
            listing.setUser(userRepository.getUserById(resultSet.getInt("USER_ID")));
            listing.setPrice(resultSet.getDouble("PRICE"));
            listing.setQuantity(resultSet.getInt("QUANTITY"));
            return listing;
        }

    }

}
