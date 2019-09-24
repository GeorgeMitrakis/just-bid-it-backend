package back.data.jdbc;

import back.model.Seller;
import org.springframework.jdbc.core.RowMapper;
import back.model.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ItemRowMapper implements RowMapper<Item>{
    private List<String> categories;

    public ItemRowMapper(List<String> categories){
        super();
        this.categories = categories;
    }

    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {

        boolean isRunning;

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currTime = myDateObj.format(myFormatObj);

        //check if auction is still running
        if(currTime.compareTo(rs.getString("end"))>0){//if auction time expired
            isRunning = false;
        }
        else if(rs.getFloat("current_bid") >= rs.getFloat("buy_price")){//if last bid is greater or equal to buy price
            isRunning = false;
        }
        else{
            isRunning = true;
        }

        Seller seller = new Seller(rs.getString("username"), rs.getLong("seller_id"), rs.getInt("seller_rating"));
        return new Item(
                rs.getInt("id"),
                seller,
                isRunning,
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
