package back.data.jdbc;

import back.model.CommonUser;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import back.data.Limits;
import back.model.User;
import back.model.Item;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.math.BigInteger;
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

    //user resource

    private Optional<User> getUserByRole(String role, Object[] par, User u) throws IncorrectResultSizeDataAccessException {
        switch (role) {
            case "common user":
                return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM common_user WHERE id = ?", par, new CommonUserRowMapper(u)));
            case "admin":
                return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM administrator WHERE id = ?", par, new AdminRowMapper(u)));
            default:
                return Optional.empty();
        }
    }

    public Optional<User> getUserByCredentials(String username, String hashedPassword) throws DataAccessException{
        try {
            String[] params = new String[]{username, hashedPassword};
            List<User> users = jdbcTemplate.query("select * from user where (username,password) = (?,?)", params, new UserRowMapper());
            if (users.size() == 1)  {
                User user = users.get(0);
                Long[] p = new Long[]{user.getId()};
                return getUserByRole(user.getRole(), p, user);
            }
            else {
                return Optional.empty();
            }
        }
        catch (Exception e) {
            System.err.println("Failed to login");
            e.printStackTrace();
            throw new DataAccessException("sth failed horribly during login"){};
        }
    }

    public long countUsers() {
        return jdbcTemplate.queryForObject("select count(*) from user", Long.class);
    }

    public List<User> getUsers(long start, long count) {
        Long[] params = new Long[]{start, count};
        return jdbcTemplate.query("select * from user limit ?, ?", params, new UserRowMapper());
    }

    public Optional<User> getUserById(Long id) {
        Long[] params = new Long[]{id};
        List<User> users = jdbcTemplate.query("select * from user where id = ?", params, new UserRowMapper());
        if (users.size() == 1)  {
            return Optional.of(users.get(0));
        }
        else {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByUsername(String username) {
        String[] params = new String[]{username};
        List<User> users = jdbcTemplate.query("select * from user where username = ?", params, new UserRowMapper());
        if (users.size() == 1)  {
            return Optional.of(users.get(0));
        }
        else {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByEmail(String email) {
        String[] params = new String[]{email};
        List<User> users = jdbcTemplate.query("select * from user, common_user where user.id=common_user.id and common_user.email = ?", params, new UserRowMapper());
        if (users.size() == 1)  {
            return Optional.of(users.get(0));
        }
        else {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        String[] params = new String[]{phoneNumber};
        List<User> users = jdbcTemplate.query("select * from user, common_user where user.id=common_user.id and common_user.phone_number = ?", params, new UserRowMapper());
        if (users.size() == 1)  {
            return Optional.of(users.get(0));
        }
        else {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByTRN(String TRN) {
        String[] params = new String[]{TRN};
        List<User> users = jdbcTemplate.query("select * from user, common_user where user.id=common_user.id and common_user.tax_registration_number = ?", params, new UserRowMapper());
        if (users.size() == 1)  {
            return Optional.of(users.get(0));
        }
        else {
            return Optional.empty();
        }
    }

    void storeUser(CommonUser commonUser, String hashedPassword) throws DataAccessException{
        try {
            // insert into user and keep id
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO user(id, username, password, role, access) VALUES (default, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, commonUser.getUsername());
                ps.setString(2, hashedPassword);
                ps.setString(3, "common user");
                ps.setString(4, "pending");
                return ps;
            }, keyHolder);
            //System.out.println(keyHolder.getKey());
            long id =  keyHolder.getKey().longValue();
            // use the same id to insert to provider
            jdbcTemplate.update("INSERT INTO common_user (id, first_name, last_name, email, phone_number, country, location, tax_registration_number, seller_rating, bidder_rating) VALUES (?, ?, ?, ?, ?, ?, ?, ?, default, default)",
                    id, commonUser.getFirstname(), commonUser.getLastname(), commonUser.getEmail(), commonUser.getPhoneNumber(), commonUser.getCountry(), commonUser.getLocation(), commonUser.getTaxRegistrationNumber());
            commonUser.setId(id);
        } catch (Exception e) {
            System.err.println("Failed to store common user");
            e.printStackTrace();
            throw new DataAccessException("could not insert user"){};
        }
    }

    //items resource
    public long countItems() {
        return jdbcTemplate.queryForObject("select count(*) from item", Long.class);
    }

    public List<Item> getItems(int userId, long start, long count) {
        Long[] params = new Long[]{(long) userId, start, count};
        return jdbcTemplate.query("select * from item where seller_id = ? limit ?, ?", params, new ItemRowMapper());
    }

    public Optional<Item> getItem(Long id) {
        Long[] params = new Long[]{id};
        List<Item> items = jdbcTemplate.query("select * from item where id = ?", params, new ItemRowMapper());
        if (items.size() == 1)  {
            return Optional.of(items.get(0));
        }
        else {
            return Optional.empty();
        }
    }
}