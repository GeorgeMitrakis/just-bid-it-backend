package back.api;

import back.data.ItemDAO;
import back.data.Limits;
import back.model.Item;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import back.conf.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemsResource extends ServerResource {

    private final ItemDAO itemDAO = Configuration.getInstance().getItemDAO();


    @Override
    protected Representation get() throws ResourceException {
        if(getQueryValue("userId").isEmpty()){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"error in userId parameter");
        }
        int userId = Integer.parseInt(getQueryValue("userId"));

        Limits limits = new Limits(0, 100);
        List<Item> items = itemDAO.getItems(userId, limits);

        Map<String, Object> map = new HashMap<>();
        map.put("start", limits.getStart());
        map.put("count", limits.getCount());
        map.put("total", limits.getTotal());
        map.put("results", items);

        return new JsonMapRepresentation(map);
    }
}

