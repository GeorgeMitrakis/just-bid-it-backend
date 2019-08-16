package back.data.jdbc;

import back.model.User;
import org.springframework.jdbc.core.RowMapper;
import back.model.CommonUser;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommonUserRowMapper implements RowMapper<CommonUser>{
    private User user;

    public CommonUserRowMapper(User _user){
        super();
        user = _user;
    }
    @Override
    public CommonUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CommonUser(
                this.user,
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone_number"),
                rs.getString("country"),
                rs.getString("location"),
                rs.getString("tax_registration_number"),
                rs.getInt("seller_rating"),
                rs.getInt("bidder_rating")
        );
    }
}
