package back.data.jdbc;

import back.model.User;
import org.springframework.jdbc.core.RowMapper;
import back.model.Administrator;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRowMapper implements RowMapper<Administrator>{
    private User user;

    public AdminRowMapper(User _user){
        super();
        user = _user;
    }
    @Override
    public Administrator mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Administrator(
                this.user,
                rs.getString("first_name"),
                rs.getString("last_name")
        );
    }
}
