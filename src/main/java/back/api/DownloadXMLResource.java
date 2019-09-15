package back.api;

import back.conf.Configuration;
import back.data.ItemDAO;
import back.model.Item;
import back.model.Items;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.xml.bind.JAXBException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadXMLResource extends ServerResource {
    private final ItemDAO itemDAO = Configuration.getInstance().getItemDAO();

    @Override
    protected Representation get() throws ResourceException {
        Items items = new  Items(itemDAO.getAllItems());

        try {
            new ItemsToXml(items).objectToXml();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("items", items);
        return new XMLRepresentation(items);
    }
}
