package back.data;

import back.model.CommonUser;
import back.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    List<User> getUsers(Limits limits);

    Optional<User> getUserByCredentials(String username, String password);

    Optional<User> getUserById(long id);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByPhoneNumber(String phoneNumber);

    Optional<User> getUserByTRN(String TRN);

    void storeUser(CommonUser commonUser, String password);

}
