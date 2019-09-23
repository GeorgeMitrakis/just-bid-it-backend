package back.data;

import back.model.CommonUser;
import back.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDAO {

    List<User> getUsers(Limits limits);

    List<CommonUser> getCommonUsers();

    Optional<User> getUserByCredentials(String username, String password);

    Optional<User> getUserById(long id);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByPhoneNumber(String phoneNumber);

    Optional<User> getUserByTRN(String TRN);

    void storeUser(CommonUser commonUser, String password);

    void grantUserAccess(String username);

    void denyUserAccess(String username);

    List<String> getUsernamesLike(String username);

}
