package back.data.jdbc;

import org.springframework.jdbc.core.RowMapper;
import back.model.CommonUser;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommonUserRowMapper implements RowMapper<CommonUser>{
    @Override
    public CommonUser mapRow(ResultSet rs, int rowNum) throws SQLException {

        long id = rs.getLong("id");
        String username = rs.getString("username");
        //String role = rs.getString("role");
        String access = rs.getString("access");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String email = rs.getString("email");
        String phoneNumber = rs.getString("phone_number");
        String country = rs.getString("country");
        String location = rs.getString("location");
        String taxRegistrationNumber = rs.getString("tax_registration_number");
        int sellerRating = rs.getInt("seller_rating");
        int bidderRating = rs.getInt("bidder_rating");

        return new CommonUser(id, username, access, firstName,lastName,email,phoneNumber,country,location, taxRegistrationNumber, sellerRating, bidderRating);
    }
}
