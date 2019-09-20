package back.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Bids {
    private List<Bid> bids;

    public Bids(List<Bid> bids) {
        this.bids = bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    @XmlElement(name="Bid")
    public List<Bid> getBids() {
        return bids;
    }

    public void addBid(Bid bid){
        bids.add(bid);
    }
}
