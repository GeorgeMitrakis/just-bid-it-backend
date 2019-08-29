package back.model;

public class Bid {
    private long id;
    private final long itemId;
    private final long bidderId;
    private final String time;
    private final float amount;

    public Bid(long id, long itemId, long bidderId, String time, float amount) {
        this.id = id;
        this.itemId = itemId;
        this.bidderId = bidderId;
        this.time = time;
        this.amount = amount;
    }

    public void setId(long id) {
        this.id = id;
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
