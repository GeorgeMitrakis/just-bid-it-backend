package back.data.jdbc;

import back.model.Bid;
import back.model.CommonUser;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import back.model.User;
import back.model.Item;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

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

    public List<CommonUser> getCommonUsers(){
        List<User> users = jdbcTemplate.query("select * from just_bid_it.user", new UserRowMapper());
        List<CommonUser> commonUsers = jdbcTemplate.query("select common_user.* from just_bid_it.user, just_bid_it.common_user as common_user where user.id = common_user.id", new CommonUserRowMapper(users));
        return commonUsers;
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

    void grantUserAccess(String username) throws DataAccessException{
        try{
            String[] params = new String[]{username};
            jdbcTemplate.update("update just_bid_it.user set access = 'granted' where username = ?", username);
        }catch (Exception e) {
            System.err.println("Failed to grant access to user");
            e.printStackTrace();
            throw new DataAccessException("could not grant access to user"){};
        }

    }

    void denyUserAccess(String username) throws DataAccessException{
        try{
            String[] params = new String[]{username};
            jdbcTemplate.update("update just_bid_it.user set access = 'denied' where username = ?", username);
        }catch (Exception e) {
            System.err.println("Failed to deny access to user");
            e.printStackTrace();
            throw new DataAccessException("could not deny access to user"){};
        }

    }

    //items resource
    private Map<String,List<String>> ListToMapConverter(List<Map<String,String>> l){
        Map<String,List<String>> m = new HashMap<>();
        Map<String,String> listElem;
        List<String> mapValue;
        for (int i = 0; i < l.size(); i++) {
            listElem = l.get(i);
            for (String itemId: listElem.keySet() ) {
                if(m.get(itemId) == null){
                    mapValue = new ArrayList<>();
                    mapValue.add(listElem.get(itemId));
                    m.put(itemId, mapValue);
                }
                else{
                    mapValue = m.get(itemId);
                    mapValue.add(listElem.get(itemId));
                }
            }
        }
        return m;
    }

    private void setItemCategories(List<Item> items, List<Map<String,String>> itemCategories){
        Map<String,List<String>> m = ListToMapConverter(itemCategories);
        System.err.println(m);
        Item item;
        for(int i=0 ; i<items.size() ; i++){
            item = items.get(i);
            item.setCategories(m.get(String.valueOf(item.getId())));
        }
    }

    public long countItems() {
        return jdbcTemplate.queryForObject("select count(*) from just_bid_it.item", Long.class);
    }

    public List<Item> getItems(int userId, long start, long count) {
        Long[] params = new Long[]{(long) userId, start, count};
        Long[] categoryParams = new Long[1];
        List<Item> items =  jdbcTemplate.query("select * from just_bid_it.item where seller_id = ? limit ?, ?", params, new ItemRowMapper(null));
        List<String> categories;
//        for(int i=0 ; i<items.size() ; i++){
//            categoryParams[0] = items.get(i).getId();
//            categories = jdbcTemplate.query("select * from just_bid_it.item_categories where item_id = ?", categoryParams, new ItemCategoriesRowMapper());
//            items.get(i).setCategories(categories);
//        }
        categoryParams[0] = (long) userId;
        List<Map<String,String>> itemCategories = jdbcTemplate.query("select item_categories.item_id as item_id, item_categories.category as category " +
                "from just_bid_it.item_categories, just_bid_it.item" +
                " where item.id = item_categories.item_id and item.seller_id = ?", categoryParams, new ICRowMapper());

        setItemCategories(items, itemCategories);

        return items;
    }

    public Optional<Item> getItemById(long id) {
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
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO just_bid_it.item(id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (default,?,?,?,?,?,?,?,default,default,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, item.getSellerId());
                    ps.setString(2, item.getName());
                    ps.setFloat(3, item.getCurrentBid());
                    ps.setFloat(4, item.getFirstBid());
                    ps.setFloat(5, item.getBuyPrice());
                    ps.setInt(6, item.getNumberOfBids());
                    ps.setString(7, item.getLocation());
                    ps.setString(8, item.getCountry());
                    ps.setString(9, item.getStart());
                    ps.setString(10, item.getEnd());
                    ps.setString(11, item.getDescription());
                    return ps;
                },keyHolder);
            }
            else{
                jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO just_bid_it.item(id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (default,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, item.getSellerId());
                    ps.setString(2, item.getName());
                    ps.setFloat(3, item.getCurrentBid());
                    ps.setFloat(4, item.getFirstBid());
                    ps.setFloat(5, item.getBuyPrice());
                    ps.setInt(6, item.getNumberOfBids());
                    ps.setString(7, item.getLocation());
                    ps.setDouble(8, item.getLatitude());
                    ps.setDouble(9, item.getLongitude());
                    ps.setString(10, item.getCountry());
                    ps.setString(11, item.getStart());
                    ps.setString(12, item.getEnd());
                    ps.setString(13, item.getDescription());
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

//    public void closeAuction(long itemId) throws DataAccessException{
//
//        try{
//            jdbcTemplate.update("update just_bid_it.item set is_running = false where id = ?", itemId);
//        }
//        catch(Exception e) {
//            System.err.println("Failed to close auction");
//            e.printStackTrace();
//            throw new DataAccessException("could not close auction"){};
//        }
//
//    }

    public void updateCurrentBid(long itemId, float amount) throws DataAccessException{
        try{
            jdbcTemplate.update("update just_bid_it.item set current_bid = ?, number_of_bids = number_of_bids +1 where id = ?",
                    amount, itemId);
        }
        catch(Exception e) {
            System.err.println("Failed to update current_bid in item");
            e.printStackTrace();
            throw new DataAccessException("could not update current_bid in item"){};
        }
    }

    // /items/{id} resource
    public void updateItem(Item item) throws DataAccessException{
        try{
            jdbcTemplate.update("update just_bid_it.item " +
                    "set " +
                    "item.name = ? , " +
                    "item.buy_price = ?, " +
                    "item.first_bid = ?, " +
                    "item.current_bid = ?, " +
                    "item.location = ?, " +
                    "item.latitude = ?, " +
                    "item.longitude = ?, " +
                    "item.country = ?, " +
                    "item.end = ?, " +
                    "item.description = ? " +
                    "where item.id = ? ",
                    item.getName(), item.getBuyPrice(), item.getFirstBid(), item.getCurrentBid(), item.getLocation(),
                    item.getLatitude(),  item.getLongitude(), item.getCountry(), item.getEnd(), item.getDescription(),
                    item.getId());
        }
        catch(Exception e) {
            System.err.println("Failed to update item");
            e.printStackTrace();
            throw new DataAccessException("could not update item"){};
        }
    }

    public void addCategoryToItem(Item item, String category){
        try{
//            jdbcTemplate.update("INSERT IGNORE INTO just_bid_it.category(id, name) VALUES (default, ?) ",
//                    categories.get(i));
//            jdbcTemplate.update("INSERT INTO just_bid_it.item_categories(item_id, category) VALUES (?, ?) ",
//                    itemId, categories.get(i));
//
            jdbcTemplate.update("insert ignore into just_bid_it.category(id, name) values (default, ?)", category);
            jdbcTemplate.update("insert ignore into just_bid_it.item_categories(item_id, category) values (?, ?) ",item.getId(), category);

        }
        catch(Exception e) {
            System.err.println("Failed to add category to item");
            e.printStackTrace();
            throw new DataAccessException("could not add category to item"){};
        }
    }

    public void removeCategoryFromItem(Item item, String category){
        try{
            jdbcTemplate.update("delete from just_bid_it.item_categories where (item_id,category) = (?,?)",
                    item.getId(), category);
        }
        catch(Exception e) {
            System.err.println("Failed to remove category from item");
            e.printStackTrace();
            throw new DataAccessException("could not remove category from item"){};
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

    //location autocomplete search resource
    public List<String> getLocationNamesByStartOfName(String substring) throws DataAccessException {
        try{
            String[] params = new String[]{substring+"%"};
            return jdbcTemplate.query("SELECT location from just_bid_it.item where location like ? group by location", params, new LocationRowMapper());
        }
        catch(Exception e) {
            System.err.println("Failed to search for locations");
            e.printStackTrace();
            throw new DataAccessException("could not search for locations"){};
        }
    }

        //item search

    private Object[] appendValue(Object[] obj, Object newObj) {

        ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
        temp.add(newObj);
        return temp.toArray();

    }

    //TODO: proper full-text search query
    public List<Item> searchItems(String searchTerm, String category, String location, Float price) throws DataAccessException{
        try{

            //build the query

            String query = "select item.* from ";
            //add fulltext search query
            if(searchTerm!=null){
                query = query + "(select item.* from just_bid_it.item where match(name, description) against(? in natural language mode ) ) as ";
            }
            query = query + "item ";
            if(category!=null){
                query = query +", just_bid_it.item_categories as item_categories "   ;
            }
            if(category!=null || location!=null || price!=null ){
                query = query+" where ";
            }
            if(category!=null){
                query = query + "item.id = item_categories.item_id and category = ? ";
                if(location!=null || price!=null){
                    query = query + " and ";
                }
            }
            if(location!=null){
                query = query + " location = ? ";
                if(price!=null){
                    query = query + " and ";
                }
            }
            if(price!=null){
                query = query + "current_bid <= ? ";
            }
            if(category!=null){
                query = query + "group by id";
            }

            System.out.println(query);


            List<Item> items;
            List<Map<String,String>> itemCategories;
            Object[] params = new Object[]{};

            //get categories
            if(category == null){
                itemCategories = jdbcTemplate.query("select * from just_bid_it.item_categories", new ICRowMapper());
            }
            else{
                String[] catParam = new String[]{category};
                itemCategories = jdbcTemplate.query("select * from just_bid_it.item_categories where category = ?",catParam , new ICRowMapper());
            }

            //get items
            if(searchTerm == null && category== null && location == null && price == null){
                items = jdbcTemplate.query("select * from just_bid_it.item", new ItemRowMapper(null));
            }
            else{
                if(searchTerm!=null){
                    params = appendValue(params, searchTerm);
                }
                if(category!=null){
                    params = appendValue(params, category);
                }
                if(location!=null){
                    params = appendValue(params, location);
                }
                if(price!=null){
                    params = appendValue(params, price);
                }

                items = jdbcTemplate.query(query, params, new ItemRowMapper(null));
            }

            //set categories to their items
            setItemCategories(items, itemCategories);

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

            String bidder  = jdbcTemplate.query("select user.username " +
                    "from just_bid_it.user , just_bid_it.bid " +
                    "where user.id = bid.bidder_id " +
                    "and bid.bidder_id = "+bid.getBidderId(),
                    (rs, rowNum)-> rs.getString("username")
                    ).get(0);

            bid.setBidder(bidder);

            Integer bidderRating  = jdbcTemplate.query("select common_user.bidder_rating " +
                            "from just_bid_it.common_user , just_bid_it.bid " +
                            "where common_user.id = bid.bidder_id " +
                            "and bid.bidder_id = "+bid.getBidderId(),
                    (rs, rowNum)-> rs.getInt("bidder_rating")
            ).get(0);

            bid.setBidderRating(bidderRating);


        }
        catch(Exception e) {
            System.err.println("Failed to insert bid in the database");
            e.printStackTrace();
            throw new DataAccessException("could not insert bid in the database"){};
        }

    }
}