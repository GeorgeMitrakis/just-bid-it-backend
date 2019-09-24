package back.api;

import back.model.Items;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class UploadResource extends ServerResource {

    @Override
    protected Representation get() throws ResourceException{
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Items.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Items items = (Items) unmarshaller.unmarshal(new FileReader("/home/george/Dropbox/10/Web_Technologies/ebay-data-2/items-0.xml"));
            Map<String,Object> map = new HashMap<>();
            map.put("items", items);
            return new JsonMapRepresentation(map);
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
        }

    }
}
