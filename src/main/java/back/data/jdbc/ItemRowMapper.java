package back.data.jdbc;

import org.springframework.jdbc.core.RowMapper;
import back.model.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemRowMapper implements RowMapper<Item>{
    private List<String> categories;

    public ItemRowMapper(List<String> categories){
        super();
        this.categories = categories;
    }

    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Item(
                rs.getInt("id"),
                rs.getInt("seller_id"),
                rs.getBoolean("is_running"),
                rs.getString("name"),
                this.categories,
                rs.getFloat("current_bid"),
                rs.getFloat("first_bid"),
                rs.getFloat("buy_price"),
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
