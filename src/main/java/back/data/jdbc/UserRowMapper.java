package back.data.jdbc;

import org.springframework.jdbc.core.RowMapper;
import back.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

class UserRowMapper implements RowMapper<User>  {

	@Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        long id = rs.getLong("id");
        String username = rs.getString("username");
        String role = rs.getString("role");
        String access = rs.getString("access");

        return new User(id, username, role, access);
    }
}