package back.api;

import back.data.Limits;
import back.data.UserDAO;
import back.model.User;
import back.util.Hashing;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import back.conf.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class LoginResource extends ServerResource {
    private final UserDAO userDAO = Configuration.getInstance().getUserDAO();

    @Override
    protected Representation post(Representation entity) throws ResourceException {

        //Create a new restlet form
        Form form = new Form(entity);
        //Read the parameters
        String username = form.getFirstValue("username");
        String password = form.getFirstValue("password");

        //validate the values (in the general case)
        if ( username == null || username.equals("")
                ||password == null || password.equals("")){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "missing or empty parameters");
        }

        // hash password
        password = Hashing.getHashSHA256(password);


        //use the DAO machinery to add the new user
        Optional<User> user = userDAO.getUserByCredentials(username, password);
        if(!user.isPresent()){
            throw new ResourceException(Status.CLIENT_ERROR_UNAUTHORIZED, "incorrect username or password");
        }


        //return a json representation of the newly created record
        //...
        Map<String, Object> map = new HashMap<>();
        map.put("result", user);
        return new JsonMapRepresentation(map);
        //throw new ResourceException(Status.SERVER_ERROR_NOT_IMPLEMENTED);
    }

}
