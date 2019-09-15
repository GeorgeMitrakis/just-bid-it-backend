package back.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class Bid {
    @XmlTransient
    private long id;

    @XmlTransient
    private final long itemId;

    @XmlTransient
    private final long bidderId;

    @XmlTransient
    private String bidder;

    @XmlTransient
    private int bidderRating;

    @XmlAttribute(name="Time")
    private final String time;

    @XmlAttribute(name="Amount")
    private final float amount;

    public Bid(long id, long itemId, long bidderId, String time, float amount) {
        this.id = id;
        this.itemId = itemId;
        this.bidderId = bidderId;
        this.time = time;
        this.amount = amount;
    }

    public Bid(long id, long itemId, long bidderId, String bidder, int bidderRating, String time, float amount) {
        this.id = id;
        this.itemId = itemId;
        this.bidderId = bidderId;
        this.bidder = bidder;
        this.bidderRating = bidderRating;
        this.time = time;
        this.amount = amount;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBidder(String bidder) {
        this.bidder = bidder;
    }

    public void setBidderRating(int bidderRating) {
        this.bidderRating = bidderRating;
    }

    public long getId() {
        return id;
    }

    public long getItemId() {
        return itemId;
    }

    public long getBidderId() {
        return bidderId;
    }

    public String getTime() {
        return time;
    }

    public float getAmount() {
        return amount;
    }
}
