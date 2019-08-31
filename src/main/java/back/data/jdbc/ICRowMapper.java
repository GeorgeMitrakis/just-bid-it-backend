package back.data.jdbc;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ICRowMapper implements RowMapper<Map<String,String>>{
    @Override
    public Map<String,String> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, String> map = new HashMap<>();
        map.put(rs.getString("item_id"), rs.getString("category"));
        return map;
    }
}
