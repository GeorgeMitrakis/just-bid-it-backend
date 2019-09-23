package back.model;

import back.util.DateTimeParser;

import java.util.List;
import javax.xml.bind.annotation.*;


public class Item {
    private long id;

    private Seller seller;

    private boolean running;

    private String name;

    private List<String> categories;

    private List<Bid> bids;

    private float currentBid;

    private float firstBid;

    private Float buyPrice;

    private int numberOfBids;

//    private String location;
//
//    private Double latitude;
//
//    private Double longitude;

    private Location location;

    private String country;

    private String start;

    private String end;

    private String description;

    public Item() {
    }



    public Item(long id,
			Seller seller,
			boolean running,
			String name,
			List<String> categories,
			List<Bid> bids,
			float currentBid,
			float firstBid,
            Float buyPrice,
			int numberOfBids,
			String location,
			Double latitude,
			Double longitude,
			String country,
			String start,
			String end,
			String description) {
        this.id = id;
        this.seller = seller;
        this.running = running;
        this.name = name;
        this.categories = categories;
        this.bids = bids;
        this.currentBid = currentBid;
        this.firstBid = firstBid;
        this.buyPrice = buyPrice;
        this.numberOfBids = numberOfBids;
//        this.location = location;
//        this.latitude = latitude;
//        this.longitude = longitude;
        this.location = new Location(location,latitude,longitude);
        this.country = country;
        this.start = DateTimeParser.parseDateTime(start);
        this.end = DateTimeParser.parseDateTime(end);
        this.description = description;
    }

    public Item(
            long id,
			boolean running,
			String name,
			List<String> categories,
			List<Bid> bids,
			float currentBid,
			float firstBid,
            Float buyPrice,
			int numberOfBids,
			String location,
			String country,
			String start,
			String end,
			String description) {
        this.id = id;
        this.seller = null;
        this.running = running;
        this.name = name;
        this.categories = categories;
        this.bids = bids;
        this.currentBid = currentBid;
        this.firstBid = firstBid;
        this.buyPrice = buyPrice;
        this.numberOfBids = numberOfBids;
//        this.location = location;
//        this.latitude = null;
//        this.longitude = null;
        this.location = new Location(location);
        this.country = country;
        this.start = DateTimeParser.parseDateTime(start);
        this.end = DateTimeParser.parseDateTime(end);
        this.description = description;
    }

    public Item(long id,
			Seller seller,
			boolean running,
			String name,
			List<String> categories,
			float currentBid,
			float firstBid,
            Float buyPrice,
			int numberOfBids,
			String location,
			Double latitude,
			Double longitude,
			String country,
			String start,
			String end,
			String description) {
        this.id = id;
        this.seller = seller;
        this.running = running;
        this.name = name;
        this.categories = categories;
        this.bids = null;
        this.currentBid = currentBid;
        this.firstBid = firstBid;
        this.buyPrice = buyPrice;
        this.numberOfBids = numberOfBids;
//        this.location = location;
//        this.latitude = latitude;
//        this.longitude = longitude;
        this.location = new Location(location,latitude,longitude);
        this.country = country;
        this.start = DateTimeParser.parseDateTime(start);
        this.end = DateTimeParser.parseDateTime(end);
        this.description = description;
    }

    public Item(long id,
			boolean running,
			String name,
			List<String> categories,
			float currentBid,
			float firstBid,
            Float buyPrice,
			int numberOfBids,
			String location,
			Double latitude,
			Double longitude,
			String country,
			String start,
			String end,
			String description) {
        this.id = id;
        this.seller = null;
        this.running = running;
        this.name = name;
        this.categories = categories;
        this.bids = null;
        this.currentBid = currentBid;
        this.firstBid = firstBid;
        this.buyPrice = buyPrice;
        this.numberOfBids = numberOfBids;
//        this.location = location;
//        this.latitude = latitude;
//        this.longitude = longitude;
        this.location = new Location(location,latitude,longitude);
        this.country = country;
        this.start = DateTimeParser.parseDateTime(start);
        this.end = DateTimeParser.parseDateTime(end);
        this.description = description;
    }

    public Item(long id,
			boolean running,
			String name,
			List<String> categories,
			float currentBid,
			float firstBid,
            Float buyPrice,
			int numberOfBids,
			String location,
			String country,
			String start,
			String end,
			String description) {
        this.id = id;
        this.seller = null;
        this.running = running;
        this.name = name;
        this.categories = categories;
        this.bids = null;
        this.currentBid = currentBid;
        this.firstBid = firstBid;
        this.buyPrice = buyPrice;
        this.numberOfBids = numberOfBids;
//        this.location = location;
//        this.latitude = null;
//        this.longitude = null;
        this.location = new Location(location);
        this.country = country;
        this.start = DateTimeParser.parseDateTime(start);
        this.end = DateTimeParser.parseDateTime(end);
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

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setFirstBid(float firstBid) {
        this.firstBid = firstBid;
    }

    public void setBuyPrice(Float buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void setLocation(String location) {
        this.location.setName(location);
    }

    public void setLatitude(Double latitude) {
//        this.latitude = latitude;
        this.location.setLatitude(latitude);
    }

    public void setLongitude(Double longitude) {
        this.location.setLongitude(longitude);
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setStart(String start) {
        this.start = DateTimeParser.parseDateTime(start);
    }

    public void setEnd(String end) {
        this.end = DateTimeParser.parseDateTime(end);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlAttribute(name="ItemID")
    public long getId() {
        return id;
    }

    @XmlElement(name="Seller")
    public Seller getSeller() {
        return seller;
    }

    @XmlTransient
    public boolean isRunning() {
        return running;
    }

    @XmlElement(name="Name")
    public String getName() {
        return name;
    }

    @XmlElement(name="Category")
    public List<String> getCategories() {
        return categories;
    }

    @XmlElementWrapper(name="Bids")
    @XmlElement(name="Bid")
    public List<Bid> getBids() {
        return bids;
    }

    @XmlElement(name="Currently")
    public float getCurrentBid() {
        return currentBid;
    }

    @XmlElement(name="First_Bid")
    public float getFirstBid() {
        return firstBid;
    }

    @XmlElement(name="Buy_Price")
    public Float getBuyPrice() {
        return buyPrice;
    }

    @XmlElement(name="Number_of_Bids")
    public int getNumberOfBids() {
        return numberOfBids;
    }

    @XmlTransient
    public String getLocation() {
        return location.getName();
    }

    @XmlElement(name="Location")
    public Location getLocationObject(){
        return location;
    }

    @XmlTransient
    public Double getLatitude() {
        return location.getLatitude();
    }

    @XmlTransient
    public Double getLongitude() {
        return location.getLongitude();
    }

    @XmlElement(name="Country")
    public String getCountry() {
        return country;
    }

    @XmlElement(name="Started")
    public String getStart() {
        return start;
    }

    @XmlElement(name="Ends")
    public String getEnd() {
        return end;
    }

    @XmlElement(name="Description")
    public String getDescription() {
        return description;
    }
}
