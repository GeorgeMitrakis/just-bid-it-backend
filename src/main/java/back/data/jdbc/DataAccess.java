package back.data.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import back.data.Limits;
import back.model.User;
import back.model.Item;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataAccess {

    private static final int MAX_TOTAL_CONNECTIONS = 16;
    private static final int MAX_IDLE_CONNECTIONS   = 8;
    
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setup(String driverClass, String url, String user, String pass) throws SQLException {

        //initialize the data source
	    BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName(driverClass);
        bds.setUrl(url);
        bds.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        bds.setMaxIdle(MAX_IDLE_CONNECTIONS);
        bds.setUsername(user);
        bds.setPassword(pass);
        bds.setValidationQuery("SELECT 1");
        bds.setTestOnBorrow(true);
        bds.setDefaultAutoCommit(true);

        //check that everything works OK        
        bds.getConnection().close();

        //initialize the jdbc template utility
        jdbcTemplate = new JdbcTemplate(bds);

        //keep the dataSource for the low-level manual example to function (not actually required)
        dataSource = bds;
    }

    //example users resource
    public long countUsers() {
        return jdbcTemplate.queryForObject("select count(*) from user", Long.class);
    }

    public List<User> getUsers(long start, long count) {
        Long[] params = new Long[]{start, count};
        return jdbcTemplate.query("select * from user limit ?, ?", params, new UserRowMapper());
    }

    public Optional<User> getUser(Long id) {
        Long[] params = new Long[]{id};
        List<User> users = jdbcTemplate.query("select * from user where id = ?", params, new UserRowMapper());
        if (users.size() == 1)  {
            return Optional.of(users.get(0));
        }
        else {
            return Optional.empty();
        }
    }

    //items resource
    public long countItems() {
        return jdbcTemplate.queryForObject("select count(*) from items", Long.class);
    }

    public List<Item> getItems(long start, long count) {
        Long[] params = new Long[]{start, count};
        return jdbcTemplate.query("select * from items limit ?, ?", params, new ItemRowMapper());
    }

    public Optional<Item> getItem(Long id) {
        Long[] params = new Long[]{id};
        List<Item> items = jdbcTemplate.query("select * from items where id = ?", params, new ItemRowMapper());
        if (items.size() == 1)  {
            return Optional.of(items.get(0));
        }
        else {
            return Optional.empty();
        }
    }
}