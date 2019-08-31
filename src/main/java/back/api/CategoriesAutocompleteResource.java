package back.api;

import back.conf.Configuration;
import back.data.CategoryDAO;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriesAutocompleteResource extends ServerResource {

    private final CategoryDAO categoryDAO = Configuration.getInstance().getCategoryDAO();

    @Override
    protected Representation get() throws ResourceException{
        if(getQueryValue("category") == null){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"no category name given");
        }

        String categoryName = getQueryValue("category");

        List<String> categories = categoryDAO.getCategoryNamesByStartOfName(categoryName);
        Map<String, Object> map = new HashMap<>();
        map.put("categories", categories);

        return new JsonMapRepresentation(map);
    }
}
