package back.model;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name="Items")
@XmlAccessorType(XmlAccessType.FIELD)
public class Items {
    @XmlElement(name="Item")
    private List<Item> items;


    public Items() {
    }

    public Items(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }
}
