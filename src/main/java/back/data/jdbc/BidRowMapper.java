package back.data.jdbc;

import back.model.Bid;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BidRowMapper implements RowMapper<Bid> {
    @Override
    public Bid mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Bid(
                rs.getInt("id"),
                rs.getInt("item_id"),
                rs.getInt("bidder_id"),
                rs.getString("username"),
                rs.getInt("bidder_rating"),
                rs.getString("time"),
                rs.getFloat("amount")
        );
    }
}
