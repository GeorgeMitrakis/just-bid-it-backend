package back.api;

import back.model.CommonUser;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import back.conf.Configuration;
import back.data.UserDAO;
import back.data.jdbc.DataAccess;
import back.data.Limits;
import back.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersResource extends ServerResource {

    private final UserDAO userDAO = Configuration.getInstance().getUserDAO();

    @Override
    protected Representation get() throws ResourceException {

        //we assume that we have the ADMIN role. Your should write code to ensure it.
        //Consult Restlet filters for implementing your code as a filter.

        //read request parameters to create the limits
        //String start = getQueryValue("start");
        //...
//        Limits limits = new Limits(0, 10);
//        List<User> users = userDAO.getUsers(limits);
        List<CommonUser> users = userDAO.getCommonUsers();
//
        Map<String, Object> map = new HashMap<>();
//        map.put("start", limits.getStart());
//        map.put("count", limits.getCount());
//        map.put("total", limits.getTotal());
        map.put("users", users);

        return new JsonMapRepresentation(map);
    }

    @Override
    protected Representation post(Representation entity) throws ResourceException {

        //we assume that the user with id = 1 is logged in
        long ownerId = 1;

        //Create a new restlet form
        Form form = new Form(entity);
        //Read the parameters
        String name = form.getFirstValue("name");
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
