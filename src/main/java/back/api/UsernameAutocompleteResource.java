package back.api;

import back.conf.Configuration;
import back.data.UserDAO;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsernameAutocompleteResource extends ServerResource {
    private final UserDAO userDAO = Configuration.getInstance().getUserDAO();

    @Override
    protected Representation get() throws ResourceException{
        if(getQueryValue("username")==null || getQueryValue("username").isEmpty()){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "missing or empty parameters");
        }
        String username = getQueryValue("username");

        List<String> usernames;
        try{
            usernames = userDAO.getUsernamesLike(username);
        }
        catch(DataAccessException e){
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL, "db query error");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("usernames", usernames);
        return new JsonMapRepresentation(map);
    }
}
