package back.api;

import back.conf.Configuration;
import back.data.ItemDAO;
import back.data.Limits;
import back.model.Item;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResource extends ServerResource {
    private final ItemDAO itemDAO = Configuration.getInstance().getItemDAO();

    @Override
    protected Representation get() throws ResourceException {
        //TODO: paginate results
//        if(getQueryValue("term") == null
//        ||getQueryValue("category") == null){
//            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"missing parameters");
//        }

        String searchTerm = getQueryValue("term");
        String category = getQueryValue("category");

        //Limits limits = new Limits(0, 50);
        List<Item> items = itemDAO.searchItems(searchTerm, category);
        Map<String, Object> map = new HashMap<>();
        map.put("total", items.size());
        map.put("items", items);

        return new JsonMapRepresentation(map);
    }
}
