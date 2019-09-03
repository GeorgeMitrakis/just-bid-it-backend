package back.data.jdbc;

import back.model.User;
import org.springframework.jdbc.core.RowMapper;
import back.model.CommonUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUserRowMapper implements RowMapper<CommonUser>{
    private User user;
    private Map<String,User> userMap;

    public CommonUserRowMapper(User _user){
        super();
        user = _user;
        userMap = null;
    }

    public CommonUserRowMapper(List<User> _userList){
        super();
        user = null;
        //userMap = _userList;
        userMap = new HashMap<>();
        for (int i = 0; i < _userList.size(); i++) {
            userMap.put(String.valueOf(_userList.get(i).getId()), _userList.get(i));
        }
    }

    @Override
    public CommonUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        if(this.user != null){
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
        else{
            return new CommonUser(
                    this.userMap.get(String.valueOf(rs.getInt("id"))),
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
}
