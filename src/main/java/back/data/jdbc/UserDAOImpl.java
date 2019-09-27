package back.data.jdbc;

import back.data.Limits;
import back.data.UserDAO;
import back.model.Bidder;
import back.model.CommonUser;
import back.model.Seller;
import back.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    private final DataAccess dataAccess;

    public UserDAOImpl(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public List<User> getUsers(Limits limits) {
        List<User> users = dataAccess.getUsers(limits.getStart(), limits.getCount());
        limits.setTotal(dataAccess.countUsers());
        return users;
    }

    @Override
    public List<CommonUser> getCommonUsers(){
        return dataAccess.getCommonUsers();
    }

    @Override
    public Optional<User> getUserByCredentials(String username, String password){
        return dataAccess.getUserByCredentials(username, password);
    }

    @Override
    public Optional<User> getUserById(long id) {
        return dataAccess.getUserById(id);
    }


    @Override
    public Optional<User> getUserByUsername(String username) {
        return dataAccess.getUserByUsername(username);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return dataAccess.getUserByEmail(email);
    }

    @Override
    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return dataAccess.getUserByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<User> getUserByTRN(String TRN) {
        return dataAccess.getUserByTRN(TRN);
    }


    @Override
    public void storeUser(CommonUser commonUser, String password) {
        dataAccess.storeUser(commonUser, password);
    }

    @Override
    public void grantUserAccess(String username){
        dataAccess.grantUserAccess(username);
    }

    @Override
    public void denyUserAccess(String username){
        dataAccess.denyUserAccess(username);
    }


    @Override
    public List<String> getUsernamesLike(String username){
        return dataAccess.getUsernamesLike(username);
    }

    @Override
    public void storeEbaySeller(Seller seller) {
        dataAccess.storeEbaySeller(seller);
    }

    @Override
    public void storeEbayBidder(Bidder bidder) {
        dataAccess.storeEbayBidder(bidder);
    }



}
