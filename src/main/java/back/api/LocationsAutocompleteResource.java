package back.api;

import back.conf.Configuration;
import back.data.LocationDAO;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationsAutocompleteResource extends ServerResource {

    private final LocationDAO locationDAO = Configuration.getInstance().getLocationDAO();

    @Override
    protected Representation get() throws ResourceException {
        if(getQueryValue("location") == null){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"no location given");
        }

        String locationName = getQueryValue("location");

        List<String> locations = locationDAO.getLocationNamesByStartOfName(locationName);
        Map<String, Object> map = new HashMap<>();
        map.put("locations", locations);

        return new JsonMapRepresentation(map);
    }
}
