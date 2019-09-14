package back.api;

import back.model.Item;
import back.model.Items;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.ArrayList;
import java.util.List;

public class ItemsToXml {
    private Items items;

    public ItemsToXml(Items items) {
        this.items = items;
    }

    public void objectToXml() throws JAXBException{
        JAXBContext jaxbContext = JAXBContext.newInstance(Items.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(items, System.out);
    }
}
