package back.data.jdbc;

import back.api.JsonMapRepresentation;
import back.model.*;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
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

    void storeUser(CommonUser commonUser, String hashedPassword) throws DataAccessException {
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

    List<String> getUsernamesLike(String prefix) throws DataAccessException{
        try{
            String[] params = new String[]{prefix+"%"};
            return jdbcTemplate.query("SELECT username from just_bid_it.user where username like ? ", params, new UsernameRowMapper());

        }
        catch (Exception e) {
            System.err.println("Failed to getUsernamesLike");
            e.printStackTrace();
            throw new DataAccessException("could not getUsernamesLike"){};
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

    private void setItemsBids(List<Item> items, List<Bid> itemBids){
        System.err.println(items.size());
        System.err.println(itemBids.size());

        Map<Long, List<Bid>> map = new HashMap<>();
        List<Bid> l;
        for (Bid b: itemBids) {
            l = map.get(b.getItemId());
            if(l == null){
                l = new ArrayList<>();
                map.put(b.getItemId(), l);
            }
            l.add(b);
        }
        System.err.println(map);
        System.err.println(map.size());
        for (Item item : items) {
            item.setBids(map.get(item.getId()));
        }
        System.err.println(new JsonMapRepresentation(map));
    }

    public void setItemsSeller(List<Item> items, List<Seller> sellers){

    }

    public long countItems() {
        return jdbcTemplate.queryForObject("select count(*) from just_bid_it.item", Long.class);
    }

    public List<Item> getItems(int userId, long start, long count) {
        Long[] params = new Long[]{(long) userId, start, count};
        Long[] categoryParams = new Long[1];
        List<Item> items =  jdbcTemplate.query("select item.*, user.username, common_user.seller_rating " +
                "from just_bid_it.item, just_bid_it.user, just_bid_it.common_user " +
                "where item.seller_id = ? " +
                "and item.seller_id = user.id " +
                "and user.id = common_user.id " +
                "limit ?, ?", params, new ItemRowMapper(null));
//        List<String> categories;
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

        Long[] bidParams = new Long[1];
        bidParams[0] = (long) userId;
        List<Bid> itemBids = jdbcTemplate.query("select bid.*, user.username, common_user.bidder_rating, common_user.location, common_user.country " +
                "from just_bid_it.bid as bid, just_bid_it.user as user, just_bid_it.common_user as common_user, just_bid_it.item as item " +
                "where bid.item_id = item.id " +
                "and bid.bidder_id = common_user.id " +
                "and user.id = common_user.id " +
                "and item.seller_id = ?", bidParams, new BidRowMapper());


        setItemsBids(items, itemBids);
        return items;
    }

    public List<Item> getAllItems(){
        List<Item> items =  jdbcTemplate.query("select item.*, user.username, common_user.seller_rating from just_bid_it.item, just_bid_it.user, just_bid_it.common_user " +
                "where user.id = common_user.id and user.id = item.seller_id", new ItemRowMapper(null));
        List<Map<String,String>> itemCategories = jdbcTemplate.query("select * from item_categories", new ICRowMapper());
        List<Bid> itemBids = jdbcTemplate.query("select bid.*, user.username, common_user.bidder_rating, common_user.location, common_user.country " +
                "from just_bid_it.bid as bid, just_bid_it.user as user, just_bid_it.common_user as common_user, just_bid_it.item as item " +
                "where bid.item_id = item.id and bid.bidder_id = common_user.id and user.id = common_user.id ", new BidRowMapper());

        setItemCategories(items, itemCategories);
        setItemsBids(items, itemBids);

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
        List<Item> items = jdbcTemplate.query("select item.*, user.username, common_user.seller_rating from just_bid_it.item, just_bid_it.user, just_bid_it.common_user " +
                " where item.id = ? and user.id = common_user.id and item.seller_id = user.id", params, new ItemRowMapper(categories));
        if (items.size() != 1) {
            return Optional.empty();
        }

        return Optional.of(items.get(0));
    }

    public void storeItem(Item item, long userId){
        try{//try storing the item itself
            KeyHolder keyHolder = new GeneratedKeyHolder();
            if((item.getLatitude() == null) || (item.getLongitude()==null)){
                jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO just_bid_it.item(id, seller_id, name, current_bid, first_bid, buy_price, number_of_bids, location, latitude, longitude, country, start, end, description) VALUES (default,?,?,?,?,?,?,?,default,default,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, userId);
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
                    ps.setLong(1, userId);
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

    public void addCategoryToItem(Item item, String category) throws DataAccessException{
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

    public void removeCategoryFromItem(Item item, String category) throws DataAccessException{
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

    public void deleteItem(long itemId) throws DataAccessException{
        try{
            jdbcTemplate.update("delete from just_bid_it.item_categories where item_id = ?", itemId);
            jdbcTemplate.update("delete from just_bid_it.item where id = ?", itemId);
        }
        catch(Exception e) {
            System.err.println("Failed to delete item");
            e.printStackTrace();
            throw new DataAccessException("could not delete item"){};
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

    //search resource

    private Object[] appendValue(Object[] obj, Object newObj) {

        ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
        temp.add(newObj);
        return temp.toArray();

    }

    private String searchQueryBuild(String searchTerm, String category, String location, Float price){
        String query = "select item.*, user.username, common_user.seller_rating from ";
        //add fulltext search query
        if(searchTerm!=null){
            query = query + "(select item.* from just_bid_it.item where match(name, description) against(? in natural language mode ) ) as ";
        }
        query = query + "item, user, common_user ";
        if(category!=null){
            query = query +", just_bid_it.item_categories as item_categories "   ;
        }
        query = query + " where item.seller_id = user.id and user.id = common_user.id ";
        if(category!=null || location!=null || price!=null ){
            query = query+" and ";
        }
        if(category!=null){
            query = query + "item.id = item_categories.item_id and category = ? ";
            if(location!=null || price!=null){
                query = query + " and ";
            }
        }
        if(location!=null){
            query = query + " item.location = ? ";
            if(price!=null){
                query = query + " and ";
            }
        }
        if(price!=null){
            query = query + "current_bid <= ? ";
        }
        if(category!=null){
            query = query + "group by item.id ";
        }

        return query;
    }

    private Object[] searchParametersBuild(String searchTerm, String category, String location, Float price){
        Object[] params = new Object[]{};
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
        for (Object o:params) {
            System.out.println(o.toString());
        }
        return params;
    }


    public List<Item> searchItems(String searchTerm, String category, String location, Float price, Long start, Long count) throws DataAccessException{
        try{

            //build the query
            String query = searchQueryBuild(searchTerm, category, location, price);
            query = query + "order by end desc, name asc ";
            query = query + "limit ?, ? ";

            System.out.println(query);

            //setup the parameters
            Object[] params = searchParametersBuild(searchTerm, category, location, price);
            params = appendValue(params, start);
            params = appendValue(params, count);

            List<Item> items;
            List<Map<String,String>> itemCategories;


            //get categories
            if(category == null){
                itemCategories = jdbcTemplate.query("select * from just_bid_it.item_categories", new ICRowMapper());
            }
            else{
                String[] catParam = new String[]{category};
                itemCategories = jdbcTemplate.query("select * from just_bid_it.item_categories where category = ?",catParam , new ICRowMapper());
            }

            //get items
            items = jdbcTemplate.query(query, params, new ItemRowMapper(null));

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

    public long countSearchedItems(String searchTerm, String category, String location, Float price) throws DataAccessException{
        try{

            //build the query
            String query = searchQueryBuild(searchTerm, category, location, price);
            query = "select count(*) from ( "+ query + " ) as item ";

            System.out.println(query);

            //setup the parameters
            Object[] params = searchParametersBuild(searchTerm, category, location, price);

            List<Item> items;
            //List<Map<String,String>> itemCategories;


            //get categories
//            if(category == null){
//                itemCategories = jdbcTemplate.query("select * from just_bid_it.item_categories", new ICRowMapper());
//            }
//            else{
//                String[] catParam = new String[]{category};
//                itemCategories = jdbcTemplate.query("select * from just_bid_it.item_categories where category = ?",catParam , new ICRowMapper());
//            }

            //get items
            if(params.length == 0){
                return jdbcTemplate.queryForObject(query, Long.class);
            }
            else{
                return jdbcTemplate.queryForObject(query, params, Long.class);
            }
            //set categories to their items
            //setItemCategories(items, itemCategories);

            //return items.size();
        }
        catch(Exception e) {
            System.err.println("Failed to search and count items");
            e.printStackTrace();
            throw new DataAccessException("could not search and count items"){};
        }
    }

    //bid item

    public List<Bid> getFullBidsInfoByItemId(long itemId) throws DataAccessException{
        try{
            Long[] params = new Long[]{itemId};
            return jdbcTemplate.query("select bid.*, user.username, common_user.bidder_rating, common_user.location, common_user.country " +
                    "from just_bid_it.bid as bid, just_bid_it.user as user, just_bid_it.common_user as common_user " +
                    "where bid.item_id = ? " +
                    "and bid.bidder_id = common_user.id " +
                    "and user.id = common_user.id", params, new BidRowMapper());
        }
        catch(Exception e) {
            System.err.println("Failed to get bid list from the database");
            e.printStackTrace();
            throw new DataAccessException("could not get bid list from the database"){};
        }
    }

    public void storeBid(Bid bid, long bidderId) throws DataAccessException{
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "insert into just_bid_it.bid(id, item_id, bidder_id, time, amount) VALUES (default,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, bid.getItemId());
                ps.setLong(2, bidderId);
                ps.setString(3,bid.getTime());
                ps.setFloat(4, bid.getAmount());
                return ps;
            },keyHolder);

            long id =  keyHolder.getKey().longValue();
            bid.setId(id);

            Long[] params = new Long[]{bidderId};
            Bidder bidder  = jdbcTemplate.queryForObject("select user.username, common_user.* " +
                    "from just_bid_it.user , just_bid_it.common_user " +
                    "where user.id = common_user.id " +
                    "and common_user.id = ?",params, new BidderRowMapper());

            bid.setBidder(bidder);



        }
        catch(Exception e) {
            System.err.println("Failed to insert bid in the database");
            e.printStackTrace();
            throw new DataAccessException("could not insert bid in the database"){};
        }

    }


    //messages
    public List<Message> getSentMessages(long userId){
        try{
            Long[] params = new Long[]{userId};
            return jdbcTemplate.query("select message.*, user.username as receiver_username " +
                            "from( " +
                            "        select message.*, user.username as sender_username " +
                            "        from just_bid_it.message, just_bid_it.common_user, just_bid_it.user " +
                            "        where message.sender_id = ? " +
                            "          and message.sender_id = common_user.id " +
                            "          and common_user.id = user.id " +
                            "        ) as message, just_bid_it.user, just_bid_it.common_user " +
                            "where  message.receiver_id = common_user.id " +
                            "  and common_user.id = user.id " +
                            " order by time desc ",
                    params, new MessageRowMapper());
        }
        catch(Exception e) {
            System.err.println("Failed to get sent messages");
            e.printStackTrace();
            throw new DataAccessException("could not get sent messages"){};
        }
    }

    public List<Message> getReceivedMessages(long userId){
        try{
            Long[] params = new Long[]{userId};
            jdbcTemplate.update("update just_bid_it.message set message.read = true where message.receiver_id = ?", userId);
            return jdbcTemplate.query("select message.*, user.username as sender_username " +
                            "from( " +
                            "        select message.*, user.username as receiver_username " +
                            "        from just_bid_it.message, just_bid_it.common_user, just_bid_it.user " +
                            "        where message.receiver_id = ? " +
                            "          and message.receiver_id = common_user.id " +
                            "          and common_user.id = user.id " +
                            "        ) as message, just_bid_it.user, just_bid_it.common_user " +
                            "where  message.sender_id = common_user.id " +
                            "  and common_user.id = user.id " +
                            " order by message.read desc, message.time desc",
                    params, new MessageRowMapper());
        }
        catch(Exception e) {
            System.err.println("Failed to get received messages");
            e.printStackTrace();
            throw new DataAccessException("could not get received messages"){};
        }
    }

    public void storeMessage(Message message){
        try{
            String[] p1 = new String[]{message.getSender()};
            Integer senderId = jdbcTemplate.queryForObject("select user.id from just_bid_it.user where username = ? ",p1, Integer.class);
            String[] p2 = new String[]{message.getReceiver()};
            Integer receiverId = jdbcTemplate.queryForObject("select user.id from just_bid_it.user where username = ? ",p2, Integer.class);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO just_bid_it.message(id, sender_id, receiver_id, text, time) VALUES (default,?,?,?,?,default) ", Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, senderId);
                ps.setInt(2, receiverId);
                ps.setString(3, message.getText());
                ps.setString(4, message.getTime());
                return ps;
            },keyHolder);

            message.setId(keyHolder.getKey().intValue());
        }
        catch(Exception e) {
            System.err.println("Failed to post message");
            e.printStackTrace();
            throw new DataAccessException("could not post message"){};
        }
    }

    public Message getMessage(int messageId){
        Integer[] params = new Integer[]{messageId};
        try{
            return jdbcTemplate.queryForObject("select message.*, user.username as sender_username " +
                    "from( " +
                    "        select message.*, user.username as receiver_username " +
                    "        from just_bid_it.message, just_bid_it.user " +
                    "        where message.receiver_id = user.id " +
                    "        ) as message, just_bid_it.user " +
                    "where  message.sender_id = user.id " +
                    "and message.id = ?", params, new MessageRowMapper());
        }
        catch(Exception e) {
            System.err.println("Failed to delete message");
            e.printStackTrace();
            throw new DataAccessException("could not delete message"){};
        }
    }

    public void deleteMessage(int messageId){
        try{
            jdbcTemplate.update("delete from just_bid_it.message where message.id = ? ", messageId);
        }
        catch(Exception e) {
            System.err.println("Failed to delete message");
            e.printStackTrace();
            throw new DataAccessException("could not delete message"){};
        }
    }

    public int getUnreadMessagesAmount(long id) {
        try{
            return jdbcTemplate.queryForObject("select count(*) " +
                    "from just_bid_it.message " +
                    "where message.read = false " +
                    "and message.receiver_id = ?", Integer.class, id);
        }
        catch(Exception e) {
            System.err.println("Failed to count messages");
            e.printStackTrace();
            throw new DataAccessException("could not count messages"){};
        }

    }
}