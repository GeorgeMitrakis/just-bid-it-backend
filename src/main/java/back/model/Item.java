package back.model;

public class Item {
    private final long id;
    private final String name;
    private final String currentBid;
    private final String firstBid;
    private final int numberOfBids;
    private final String country;
    private final String started;
    private final String ends;
    private final String description;


    public Item(
            long id,
            String name,
            String currentBid,
            String firstBid,
            int numberOfBids,
            String country,
            String started,
            String ends,
            String description) {
        this.id = id;
        this.name = name;
        this.currentBid = currentBid;
        this.firstBid = firstBid;
        this.numberOfBids = numberOfBids;
        this.country = country;
        this.started = started;
        this.ends = ends;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCurrentBid() {
        return currentBid;
    }

    public String getFirstBid() {
        return firstBid;
    }

    public int getNumberOfBids() {
        return numberOfBids;
    }

    public String getCountry() {
        return country;
    }

    public String getStarted() {
        return started;
    }

    public String getEnds() {
        return ends;
    }

    public String getDescription() {
        return description;
    }
}
