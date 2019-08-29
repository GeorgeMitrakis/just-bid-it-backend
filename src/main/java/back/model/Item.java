package back.model;

import java.util.List;

public class Item {
    private long id;
    private final long sellerId;
    private boolean running;
    private final String name;
    private List<String> categories;
    private float currentBid;
    private final float firstBid;
    private final float buyPrice;
    private int numberOfBids;
    private final String location;
    private final Double latitude;
    private final Double longitude;
    private final String country;
    private final String start;
    private final String end;
    private final String description;


    public Item(long id, long sellerId, boolean running, String name, List<String> categories, float currentBid, float firstBid, float buyPrice, int numberOfBids, String location, Double latitude, Double longitude, String country, String start, String end, String description) {
        this.id = id;
        this.sellerId = sellerId;
        this.running = running;
        this.name = name;
        this.categories = categories;
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

    public Item(long id, long sellerId, boolean running, String name,  List<String> categories, float currentBid, float firstBid, float buyPrice,  int numberOfBids, String location, String country, String start, String end, String description) {
        this.id = id;
        this.sellerId = sellerId;
        this.running = running;
        this.name = name;
        this.categories = categories;
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

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setCurrentBid(float currentBid) {
        this.currentBid = currentBid;
    }

    public void setNumberOfBids(int numberOfBids) {
        this.numberOfBids = numberOfBids;
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
