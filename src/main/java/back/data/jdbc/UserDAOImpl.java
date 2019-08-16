package back.data.jdbc;

import back.data.Limits;
import back.data.UserDAO;
import back.model.CommonUser;
import back.model.User;

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
    public void storeUser(CommonUser commonUser, String password){
        dataAccess.storeUser(commonUser, password);
    }
}
