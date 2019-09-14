package back.api;

import back.conf.Configuration;
import back.data.ItemDAO;
import back.model.Item;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadJSONResource extends ServerResource {
    private final ItemDAO itemDAO = Configuration.getInstance().getItemDAO();

    @Override
    protected Representation get() throws ResourceException{
        List<Item> items = itemDAO.getAllItems();

        Map<String,Object> map = new HashMap<>();
        map.put("items", items);
        return new JsonMapRepresentation(map);
    }
}
