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
import java.util.List;
import java.util.Map;

public class MessagesReceivedResource extends ServerResource {
    private final MessageDAO messageDAO = Configuration.getInstance().getMessageDAO();

    @Override
    protected Representation get() throws ResourceException {

        if(getQueryValue("userId") == null || getQueryValue("userId").isEmpty()){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"no userId parameter given");
        }
        int userId = Integer.parseInt(getQueryValue("userId"));
        List<Message> messages;

        try{
            messages = messageDAO.getReceivedMessages((long) userId);
        }
        catch (DataAccessException e){
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL, "query in database failed");
        }

        Map<String,Object> map = new HashMap<>();
        map.put("total:", messages.size());
        map.put("messages:", messages);

        return new JsonMapRepresentation(map);
    }
}
