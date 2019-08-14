package back.data.jdbc;

import org.springframework.jdbc.core.RowMapper;
import back.model.Item;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemRowMapper implements RowMapper<Item>{

    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {

        long id = rs.getLong("ItemID");
        String name = rs.getString("Name");
        String currentBid = rs.getString("Currently");
        String firstBid = rs.getString("First_Bid");
        int numberOfBids = rs.getInt("Number_of_Bids");
        String country = rs.getString("Country");
        String started = rs.getString("Started");
        String ends = rs.getString("Ends");
        String description = rs.getString("Description");

        return new Item( id, name, currentBid, firstBid, numberOfBids, country, started, ends, description);
    }
}
