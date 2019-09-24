package back.data.jdbc;

import back.model.Bidder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BidderRowMapper implements RowMapper<Bidder> {

    @Override
    public Bidder mapRow(ResultSet rs, int rowNum) throws SQLException{
        return new Bidder(
                rs.getString("username"),
                rs.getInt("bidder_rating"),
                rs.getString("location"),
                rs.getString("country")
        );
    }
}
