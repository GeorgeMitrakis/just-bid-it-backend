package back.model;

import back.util.DateTimeParser;

import javax.xml.bind.annotation.*;

public class Bid {
    private long id;

    private long itemId;

    private Bidder bidder;

    private String time;

    private float amount;

    public Bid() {
    }

    public Bid(long id, long itemId, String time, float amount) {
        this.id = id;
        this.itemId = itemId;
        this.bidder = null;
        this.time = DateTimeParser.parseDateTime(time);
        this.amount = amount;
    }

    public Bid(long id, long itemId, Bidder bidder, String time, float amount) {
        this.id = id;
        this.itemId = itemId;
        this.bidder = bidder;
        this.time = DateTimeParser.parseDateTime(time);
        this.amount = amount;
    }

    public Bid(long id, long itemId, String bidderUsername, int bidderRating, String bidderLocation, String bidderCountry, String time, float amount) {
        this.id = id;
        this.itemId = itemId;
        this.bidder = new Bidder(bidderUsername, bidderRating, bidderLocation, bidderCountry);
        this.time = DateTimeParser.parseDateTime(time);
        this.amount = amount;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public void setBidder(Bidder bidder) {
        this.bidder = bidder;
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

    @XmlTransient
    public long getItemId() {
        return itemId;
    }

    @XmlElement(name="Time")
    public String getTime() {
        return time;
    }

    @XmlElement(name="Amount")
    public float getAmount() {
        return amount;
    }

    @XmlElement(name="Bidder")
    public Bidder getBidder() {
        return bidder;
    }
}
