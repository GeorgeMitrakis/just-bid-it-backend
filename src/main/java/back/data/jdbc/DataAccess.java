package back.data.jdbc;

import back.model.Bid;
import back.model.CommonUser;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import back.model.User;
import back.model.Item;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
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
                return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM just_bid_it.common_user WHERE id = ?", par, new CommonUserRowMapper(u)));
            case "admin":
                return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM just_bid_it.administrator WHERE id = ?", par, new AdminRowMapper(u)));
            default:
                return Optional.empty();
        }
    }

    public Optional<User> getUserByCredentials(String username, String hashedPassword) throws DataAccessException{
        try {
            String[] params = new String[]{username, hashedPassword};
            List<User> users = jdbcTemplate.query("select * from just_bid_it.user where (username,password) = (?,?)", params, new UserRowMapper());
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
        return jdbcTemplate.queryForObject("select count(*) from just_bid_it.user", Long.class);
    }

    public List<User> getUsers(long start, long count) {
        Long[] params = new Long[]{start, count};
        return jdbcTemplate.query("select * from just_bid_it.user limit ?, ?", params, new UserRowMapper());
    }

    public Optional<User> getUserById(Long id) {
        Long[] params = new Long[]{id};
        List<User> users = jdbcTemplate.query("select * from just_bid_it.user where id = ?", params, new UserRowMapper());
        if (users.size() == 1)  {
            return Optional.of(users.get(0));
        }
        else {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByUsername(String username) {
        String[] params = new String[]{username};
        List<User> users = jdbcTemplate.query("select * from just_bid_it.user where username = ?", params, new UserRowMapper());
        if (users.size() == 1)  {
            return Optional.of(users.get(0));
        }
        else {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByEmail(String email) {
        String[] params = new String[]{email};
        List<User> users = jdbcTemplate.query("select * from just_bid_it.user, just_bid_it.common_user where user.id=common_user.id and common_user.email = ?", params, new UserRowMapper());
        if (users.size() == 1)  {
            return Optional.of(users.get(0));
        }
        else {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        String[] params = new String[]{phoneNumber};
        List<User> users = jdbcTemplate.query("select * from just_bid_it.user, just_bid_it.common_user where user.id=common_user.id and common_user.phone_number = ?", params, new UserRowMapper());
        if (users.size() == 1)  {
            return Optional.of(users.get(0));
        }
        else {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByTRN(String TRN) {
        String[] params = new String[]{TRN};
        List<User> users = jdbcTemplate.query("select * from just_bid_it.user, just_bid_it.common_user where user.id=common_user.id and common_user.tax_registration_number = ?", params, new UserRowMapper());
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
                PreparedStatement ps = connection.prepareStatement("INSERT INTO just_bid_it.user(id, username, password, role, access) VALUES (default, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, commonUser.getUsername());
                ps.setString(2, hashedPassword);
                ps.setString(3, "common user");
                ps.setString(4, "pending");
                return ps;
            }, keyHolder);
            //System.out.println(keyHolder.getKey());
            long id =  keyHolder.getKey().longValue();
            // use the same id to insert to provider
            jdbcTemplate.update("INSERT INTO just_bid_it.common_user (id, first_name, last_name, email, phone_number, country, location, tax_registration_number, seller_rating, bidder_rating) VALUES (?, ?, ?, ?, ?, ?, ?, ?, default, default)",
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
        return jdbcTemplate.queryForObject("select count(*) from just_bid_it.item", Long.class);
    }

    public List<Item> getItems(int userId, long start, long count) {
        Long[] params = new Long[]{(long) userId, start, count};
        Long[] categoryParams = new Long[1];
        List<Item> items =  jdbcTemplate.query("select * from just_bid_it.item where seller_id = ? limit ?, ?", params, new ItemRowMapper(null));
        List<String> categories;
        for(int i=0 ; i<items.size() ; i++){
            categoryParams[0] = items.get(i).getId();
            categories = jdbcTemplate.query("select * from just_bid_it.item_categories where item_id = ?", categoryParams, new ItemCategoriesRowMapper());
            items.get(i).setCategories(categories);
        }

        return items;
    }

    public Optional<Item> getItemById(Long id) {
        Long[] params = new Long[]{id};
        //bring the item's categories
        List<String> categories = jdbcTemplate.query("select * from just_bid_it.item_categories where item_id = ?", params, new ItemCategoriesRowMapper());
        if (categories.size() == 0){
            return Optional.empty();
        }
        //append them to the item
        List<Item> items = jdbcTemplate.query("select * from just_bid_it.item where id = ?", params, new ItemRowMapper(categories));
        if (items.size() != 1) {
            return Optional.empty();
        }

        return Optional.of(items.get(0));
    }

    public void storeItem(Item item){
        try{//try storing the item itself
            KeyHolder keyHolder = new GeneratedKeyHolder();
            if((item.getLatitude() == null) || (item.getLongitude()==null)){
                jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO just_bid_it.item(id, seller_id, is_running, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (default,?,?,?,?,?,?,?,?,default,default,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, item.getSellerId());
                    ps.setBoolean(2, item.isRunning());
                    ps.setString(3, item.getName());
                    ps.setFloat(4, item.getCurrentBid());
                    ps.setFloat(5, item.getFirstBid());
                    ps.setFloat(6, item.getBuyPrice());
                    ps.setInt(7, item.getNumberOfBids());
                    ps.setString(8, item.getLocation());
                    ps.setString(9, item.getCountry());
                    ps.setString(10, item.getStart());
                    ps.setString(11, item.getEnd());
                    ps.setString(12, item.getDescription());
                    return ps;
                },keyHolder);
            }
            else{
                jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO just_bid_it.item(id, seller_id, is_running, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (default,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, item.getSellerId());
                    ps.setBoolean(2, item.isRunning());
                    ps.setString(3, item.getName());
                    ps.setFloat(4, item.getCurrentBid());
                    ps.setFloat(5, item.getFirstBid());
                    ps.setFloat(6, item.getBuyPrice());
                    ps.setInt(7, item.getNumberOfBids());
                    ps.setString(8, item.getLocation());
                    ps.setDouble(9, item.getLatitude());
                    ps.setDouble(10, item.getLongitude());
                    ps.setString(11, item.getCountry());
                    ps.setString(12, item.getStart());
                    ps.setString(13, item.getEnd());
                    ps.setString(14, item.getDescription());
                    return ps;
                },keyHolder);
            }


            long id =  keyHolder.getKey().longValue();
            item.setId(id);

        }
        catch (Exception e) {
            System.err.println("Failed to store item");
            e.printStackTrace();
            throw new DataAccessException("could not insert item"){};
        }


        try{//try storing its categories
            long itemId = item.getId();
            List<String> categories = item.getCategories();
            for(int i=0; i<categories.size() ; i++){
                jdbcTemplate.update("INSERT IGNORE INTO just_bid_it.category(id, name) VALUES (default, ?) ",
                        categories.get(i));
                jdbcTemplate.update("INSERT INTO just_bid_it.item_categories(item_id, category) VALUES (?, ?) ",
                        itemId, categories.get(i));
            }
        }
        catch (Exception e) {
            System.err.println("Failed to store categories");
            e.printStackTrace();
            throw new DataAccessException("could not insert categories"){};
        }
    }

    public void closeAuction(long itemId) throws DataAccessException{

        try{
            jdbcTemplate.update("update just_bid_it.item set is_running = false where id = ?", itemId);
        }
        catch(Exception e) {
            System.err.println("Failed to close auction");
            e.printStackTrace();
            throw new DataAccessException("could not close auction"){};
        }

    }

    public void updateCurrentBid(long itemId, float amount) throws DataAccessException{
        try{
            jdbcTemplate.update("update just_bid_it.item set current_bid = ? where id = ?",
                    amount, itemId);
        }
        catch(Exception e) {
            System.err.println("Failed to update current_bid in item");
            e.printStackTrace();
            throw new DataAccessException("could not update current_bid in item"){};
        }
    }

    //category autocomplete search resource
    public List<String> getCategoryNamesByStartOfName(String substring) throws DataAccessException{
        try{
            String[] params = new String[]{substring+"%"};
            return jdbcTemplate.query("SELECT name from just_bid_it.category where name like ?", params, new CategoryRowMapper());
        }
        catch(Exception e) {
            System.err.println("Failed to search for categories");
            e.printStackTrace();
            throw new DataAccessException("could not search for categories"){};
        }
    }

    //item search
    //TODO: proper full-text search query
    public List<Item> searchItems(String searchTerm, String category) throws DataAccessException{
        try{
            //String[] params = new String[]{searchTerm, category};
            Long[] categoryParams = new Long[1];
            List<String> categories;
            List<Item> items =  jdbcTemplate.query("select * from just_bid_it.item", new ItemRowMapper(null));
            for(int i=0 ; i<items.size() ; i++){
                categoryParams[0] = items.get(i).getId();
                categories = jdbcTemplate.query("select * from just_bid_it.item_categories where item_id = ?", categoryParams, new ItemCategoriesRowMapper());
                items.get(i).setCategories(categories);
            }

            return items;
        }
        catch(Exception e) {
            System.err.println("Failed to search for items");
            e.printStackTrace();
            throw new DataAccessException("could not search for items"){};
        }
    }

    //bid item
    public void storeBid(Bid bid) throws DataAccessException{
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "insert into just_bid_it.bid(id, item_id, bidder_id, time, amount) VALUES (default,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, bid.getItemId());
                ps.setLong(2, bid.getBidderId());
                ps.setString(3,bid.getTime());
                ps.setFloat(4, bid.getAmount());
                return ps;
            },keyHolder);
            long id =  keyHolder.getKey().longValue();
            bid.setId(id);
        }
        catch(Exception e) {
            System.err.println("Failed to insert bid in the database");
            e.printStackTrace();
            throw new DataAccessException("could not insert bid in the database"){};
        }

    }
}