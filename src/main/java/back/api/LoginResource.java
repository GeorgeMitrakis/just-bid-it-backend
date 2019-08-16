package back.api;

import back.data.Limits;
import back.data.UserDAO;
import back.model.User;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import back.conf.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginResource extends ServerResource {
    private final UserDAO userDAO = Configuration.getInstance().getUserDAO();

    @Override
    protected Representation post(Representation entity) throws ResourceException {

        //we assume that the user with id = 1 is logged in
        //long ownerId = 1;

        //Create a new restlet form
        //Form form = new Form(entity);
        //Read the parameters
        //String name = form.getFirstValue("name");
        //...

        //validate the values (in the general case)
        //...

        //use the DAO machinery to add the new user
        //...

        //return a json representation of the newly created record
        //...

        throw new ResourceException(Status.SERVER_ERROR_NOT_IMPLEMENTED);
    }

}
