package back.api;

import back.conf.Configuration;
import back.data.ItemDAO;
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
        //TODO: correct search term checking
//        if(getQueryValue("term") == null || getQueryValue("term").isEmpty()
//        ||getQueryValue("category") == null || getQueryValue("category").isEmpty()){
//            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"no category name given");
//        }

        String searchTerm = getQueryValue("term");
        String category = getQueryValue("category");

        List<Item> items = itemDAO.searchItems(searchTerm, category);
        Map<String, Object> map = new HashMap<>();
        map.put("items", items);

        return new JsonMapRepresentation(map);
    }
}
