package back.api;

import back.data.Limits;
import back.data.UserDAO;
import back.model.CommonUser;
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

import back.util.Hashing;
import org.springframework.dao.DataAccessException;

public class SignupResource extends ServerResource {
    private final UserDAO userDAO = Configuration.getInstance().getUserDAO();

    @Override
    protected Representation post(Representation entity) throws ResourceException {

        //we assume that the user with id = 1 is logged in
        //long ownerId = 1;

        //Create a new restlet form
        Form form = new Form(entity);
        //Read the parameters
        String username = form.getFirstValue("username");
        String password = form.getFirstValue("password");
        String password1 = form.getFirstValue("password1");
        String firstname = form.getFirstValue("firstname");
        String lastname = form.getFirstValue("lastname");
        String email = form.getFirstValue("email");
        String phoneNumber = form.getFirstValue("phone_number");
        String country = form.getFirstValue("country");
        String location = form.getFirstValue("location");
        String taxRegistrationNumber = form.getFirstValue("tax_registration_number");

        //validate the values
        if ( username == null || username.equals("")
            ||password == null || password.equals("")
            ||password1 == null || password1.equals("")
            ||firstname == null || firstname.equals("")
            ||lastname == null || lastname.equals("")
            ||email == null || email.equals("")
            ||phoneNumber == null || phoneNumber.equals("")
            ||country == null || country.equals("")
            ||location == null || location.equals("")
            ||taxRegistrationNumber == null || taxRegistrationNumber.equals("")
                 ){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "missing or empty parameters");
        }
        else if(!password.equals(password1)){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "passwords don't match");
        }
        else if (userDAO.getUserByUsername(username).isPresent()){
            throw new ResourceException(Status.CLIENT_ERROR_CONFLICT,"username is already taken");
        }
        else if (userDAO.getUserByEmail(email).isPresent()){
            throw new ResourceException(Status.CLIENT_ERROR_CONFLICT,"email is already taken");
        }
        else if (userDAO.getUserByPhoneNumber(phoneNumber).isPresent()){
            throw new ResourceException(Status.CLIENT_ERROR_CONFLICT,"phone number is already in use");
        }
        else if (userDAO.getUserByTRN(taxRegistrationNumber).isPresent()){
            throw new ResourceException(Status.CLIENT_ERROR_CONFLICT,"tax registration number is already in use");
        }

        // hash password
        password = Hashing.getHashSHA256(password);

        //use the DAO machinery to add the new user
        CommonUser commonUser = new CommonUser(0,username, "pending", firstname, lastname, email, phoneNumber,country,location, taxRegistrationNumber, 0, 0);
        try{
            userDAO.storeUser(commonUser, password);
        }
        catch (DataAccessException e){
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL, "user insertion in database failed");
        }
        //return a json representation of the newly created record
        Map<String, Object> map = new HashMap<>();
        map.put("result", commonUser);
        return new JsonMapRepresentation(map);
    }

}