package back.api;

import back.conf.Configuration;
import back.data.MessageDAO;
import back.data.UserDAO;
import back.model.Message;
import back.model.User;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MessageSendResource extends ServerResource {
    private final MessageDAO messageDAO = Configuration.getInstance().getMessageDAO();
    private final UserDAO userDAO = Configuration.getInstance().getUserDAO();

    @Override
    protected Representation post(Representation entity) throws ResourceException{

        Form form = new Form(entity);
        if(form.getFirstValue("userId")==null || form.getFirstValue("userId").isEmpty()
        || form.getFirstValue("text")==null || form.getFirstValue("text").isEmpty()){
            System.err.println(form);
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "missing or empty parameters");
        }

        int userId = Integer.parseInt(form.getFirstValue("userId"));
        String receiver = getAttribute("username");
        String text = form.getFirstValue("text");


        Optional<User> userOptional = userDAO.getUserById(userId);
        if(!userOptional.isPresent()){
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
        }

        Message message = new Message(0, userOptional.get().getUsername(), receiver, text);

        try{
            messageDAO.storeMessage(message);
        }
        catch (DataAccessException e){
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL, "message insertion in database failed");
        }

        Map<String,Object> map = new HashMap<>();
        map.put("message", message);

        return new JsonMapRepresentation(map);
    }
}
