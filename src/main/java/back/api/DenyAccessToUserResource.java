package back.api;

import back.conf.Configuration;
import back.data.UserDAO;
import back.model.User;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DenyAccessToUserResource extends ServerResource {
    private final UserDAO userDAO = Configuration.getInstance().getUserDAO();

    @Override
    protected Representation post(Representation entity) throws ResourceException {

        Form form = new Form(entity);
        //String adminId = form.getFirstValue("id");

        String username = getAttribute("username");

        Optional<User> userOptional =  userDAO.getUserByUsername(username);
        if(!userOptional.isPresent()){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"username doesn't exist");
        }

        userDAO.denyUserAccess(username);
        userOptional.get().setAccess("granted");
        Map<String, Object> map = new HashMap<>();
        map.put("user", userOptional.get());

        return new JsonMapRepresentation(map);
    }
}
