package back.model;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="Bid")
public class Bid {
    private long id;

    private long itemId;

    private long bidderId;

    private String bidder;

    private int bidderRating;

    private String time;

    private float amount;

    public Bid() {
    }

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

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public void setBidderId(long bidderId) {
        this.bidderId = bidderId;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @XmlTransient
    public long getId() {
        return id;
    }

    public long getItemId() {
        return itemId;
    }

    public long getBidderId() {
        return bidderId;
    }

    @XmlElement(name="Time")
    public String getTime() {
        return time;
    }

    @XmlElement(name="Amount")
    public float getAmount() {
        return amount;
    }

    public String getBidder() {
        return bidder;
    }

    public int getBidderRating() {
        return bidderRating;
    }
}
