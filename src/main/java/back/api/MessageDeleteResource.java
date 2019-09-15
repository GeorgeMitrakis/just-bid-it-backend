package back.api;

import back.conf.Configuration;
import back.data.MessageDAO;
import back.model.Message;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.Map;

public class MessageDeleteResource extends ServerResource {
    private final MessageDAO messageDAO = Configuration.getInstance().getMessageDAO();

    @Override
    protected Representation delete() throws ResourceException{
        int messageId = Integer.parseInt(getAttribute("id"));
        Message message;
        try{
            message = messageDAO.getMessage(messageId);
            messageDAO.deleteMessage(messageId);
        }
        catch (DataAccessException e){
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL, "message deletion in database failed");
        }

        Map<String,Object> map = new HashMap<>();
        map.put("message:", message);
        return new JsonMapRepresentation(map);
    }
}
