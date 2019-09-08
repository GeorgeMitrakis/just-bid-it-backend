package back.model;

public class Bid {
    private long id;
    private final long itemId;
    private final long bidderId;
    private String bidder;
    private int bidderRating;
    private final String time;
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
