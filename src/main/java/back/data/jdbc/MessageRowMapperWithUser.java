package back.data.jdbc;

import back.model.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageRowMapperWithUser implements RowMapper<Message> {

    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Message(
                rs.getInt("id"),
                rs.getString("sender_username"),
                rs.getString("receiver_username"),
                rs.getString("text"),
                rs.getString("time")
        );
    }
}
