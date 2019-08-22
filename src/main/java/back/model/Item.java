package back.model;

public class Item {
    private long id;
    private final long sellerId;
    private final boolean running;
    private final String name;
    private final float currentBid;
    private final float firstBid;
    private final float buyPrice;
    private final int numberOfBids;
    private final String location;
    private final double latitude;
    private final double longitude;
    private final String country;
    private final String start;
    private final String end;
    private final String description;


    public Item(long id, long sellerId, boolean running, String name, float currentBid, float firstBid, float buyPrice, int numberOfBids, String location, double latitude, double longitude, String country, String start, String end, String description) {
        this.id = id;
        this.sellerId = sellerId;
        this.running = running;
        this.name = name;
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

    public Item(long id, long sellerId, boolean running, String name, float currentBid, float firstBid, float buyPrice,  int numberOfBids, String location, String country, String start, String end, String description) {
        this.id = id;
        this.sellerId = sellerId;
        this.running = running;
        this.name = name;
        this.currentBid = currentBid;
        this.firstBid = firstBid;
        this.buyPrice = buyPrice;
        this.numberOfBids = numberOfBids;
        this.location = location;
        this.latitude = Double.NaN;
        this.longitude = Double.NaN;
        this.country = country;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
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
