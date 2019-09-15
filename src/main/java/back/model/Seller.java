package back.model;

public class Seller{
    private String username;
    private long id;
    private int rating;

    public Seller(CommonUser user){
//        super(user);
        this.id = user.getId();
        this.rating = user.getSellerRating();
        this.username = user.getUsername();
    }

    public Seller(String username, long id, int rating) {
        this.username = username;
        this.id = id;
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
