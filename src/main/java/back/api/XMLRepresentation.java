package back.api;

import back.model.Items;
import org.restlet.data.MediaType;
import org.restlet.representation.WriterRepresentation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class XMLRepresentation extends WriterRepresentation {

    private Items items;

    public XMLRepresentation(Items items) {
        super(MediaType.APPLICATION_XML);
        this.items = items;
    }

    @Override
    public void write(Writer writer) throws IOException {
        try {
            JAXBContext jaxbContext  = JAXBContext.newInstance(Items.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(items, writer);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
