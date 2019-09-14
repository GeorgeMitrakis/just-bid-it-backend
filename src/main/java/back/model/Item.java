package back.model;

import java.util.List;
import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
public class Item {
    @XmlElement(name="ItemID")
    private long id;
    @XmlElement
    private long sellerId;
    @XmlElement
    private boolean running;
    @XmlElement
    private String name;
    @XmlElement
    private List<String> categories;
    @XmlElement
    private List<Bid> bids;
    @XmlElement
    private float currentBid;
    @XmlElement
    private float firstBid;
    @XmlElement
    private float buyPrice;
    @XmlElement
    private int numberOfBids;
    @XmlElement
    private String location;
    @XmlElement
    private Double latitude;
    @XmlElement
    private Double longitude;
    @XmlElement
    private String country;
    @XmlElement
    private String start;
    @XmlElement
    private String end;
    @XmlElement
    private String description;

    public Item() {
    }

    public Item(long id, long sellerId, boolean running, String name, List<String> categories, List<Bid> bids, float currentBid, float firstBid, float buyPrice, int numberOfBids, String location, Double latitude, Double longitude, String country, String start, String end, String description) {
        this.id = id;
        this.sellerId = sellerId;
        this.running = running;
        this.name = name;
        this.categories = categories;
        this.bids = bids;
        this.currentBid = currentBid;
        this.firstBid = firstBid;
        this.buyPrice = buyPrice;
        this.numberOfBids = numberOfBids;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public Item(long id, long sellerId, boolean running, String name, List<String> categories, List<Bid> bids, float currentBid, float firstBid, float buyPrice, int numberOfBids, String location, String country, String start, String end, String description) {
        this.id = id;
        this.sellerId = sellerId;
        this.running = running;
        this.name = name;
        this.categories = categories;
        this.bids = bids;
        this.currentBid = currentBid;
        this.firstBid = firstBid;
        this.buyPrice = buyPrice;
        this.numberOfBids = numberOfBids;
        this.location = location;
        this.latitude = null;
        this.longitude = null;
        this.country = country;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public Item(long id, long sellerId, boolean running, String name, List<String> categories, float currentBid, float firstBid, float buyPrice, int numberOfBids, String location, Double latitude, Double longitude, String country, String start, String end, String description) {
        this.id = id;
        this.sellerId = sellerId;
        this.running = running;
        this.name = name;
        this.categories = categories;
        this.bids = null;
        this.currentBid = currentBid;
        this.firstBid = firstBid;
        this.buyPrice = buyPrice;
        this.numberOfBids = numberOfBids;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public Item(long id, long sellerId, boolean running, String name, List<String> categories, float currentBid, float firstBid, float buyPrice, int numberOfBids, String location, String country, String start, String end, String description) {
        this.id = id;
        this.sellerId = sellerId;
        this.running = running;
        this.name = name;
        this.categories = categories;
        this.bids = null;
        this.currentBid = currentBid;
        this.firstBid = firstBid;
        this.buyPrice = buyPrice;
        this.numberOfBids = numberOfBids;
        this.location = location;
        this.latitude = null;
        this.longitude = null;
        this.country = country;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public void setCurrentBid(float currentBid) {
        this.currentBid = currentBid;
    }

    public void setNumberOfBids(int numberOfBids) {
        this.numberOfBids = numberOfBids;
    }

    public void setFirstBid(float firstBid) {
        this.firstBid = firstBid;
    }

    public void setBuyPrice(float buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public long getSellerId() {
        return sellerId;
    }

    public boolean isRunning() {
        return running;
    }

    public String getName() {
        return name;
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public float getCurrentBid() {
        return currentBid;
    }

    public float getFirstBid() {
        return firstBid;
    }

    public float getBuyPrice() {
        return buyPrice;
    }

    public int getNumberOfBids() {
        return numberOfBids;
    }

    public String getLocation() {
        return location;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getDescription() {
        return description;
    }
}
