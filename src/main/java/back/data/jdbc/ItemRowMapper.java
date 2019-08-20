package back.data.jdbc;

import org.springframework.jdbc.core.RowMapper;
import back.model.Item;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemRowMapper implements RowMapper<Item>{

    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {

        if(Double.isNaN(rs.getDouble("latitude")) || Double.isNaN(rs.getDouble("longitude"))){
            return new Item(
                    rs.getInt("id"),
                    rs.getInt("seller_id"),
                    rs.getBoolean("is_running"),
                    rs.getString("name"),
                    rs.getFloat("current_bid"),
                    rs.getFloat("first_bid"),
                    rs.getInt("number_of_bids"),
                    rs.getString("location"),
                    rs.getString("country"),
                    rs.getString("start"),
                    rs.getString("end"),
                    rs.getString("description"));
        }
        else{
            return new Item(
                    rs.getInt("id"),
                    rs.getInt("seller_id"),
                    rs.getBoolean("is_running"),
                    rs.getString("name"),
                    rs.getFloat("current_bid"),
                    rs.getFloat("first_bid"),
                    rs.getInt("number_of_bids"),
                    rs.getString("location"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude"),
                    rs.getString("country"),
                    rs.getString("start"),
                    rs.getString("end"),
                    rs.getString("description"));
        }
    }
}
