package back.model;

public class CommonUser extends  User {
    private final String firstname;
    private final String lastname;
    private final String email;
    private final String phoneNumber;
    private final String country;
    private final String location;
    private final String taxRegistrationNumber;
    private final int sellerRating;
    private final int bidderRating;

    public CommonUser(long id, String username, String access, String firstname, String lastname, String email, String phoneNumber, String country, String location, String taxRegistrationNumber, int sellerRating, int bidderRating) {
        super(id, username, "common user", access);
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.location = location;
        this.taxRegistrationNumber = taxRegistrationNumber;
        this.sellerRating = sellerRating;
        this.bidderRating = bidderRating;
    }

    public CommonUser(User user, String firstname, String lastname, String email, String phoneNumber, String country, String location, String taxRegistrationNumber, int sellerRating, int bidderRating) {
        super(user.getId(), user.getUsername(), "common user", user.getAccess());
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.location = location;
        this.taxRegistrationNumber = taxRegistrationNumber;
        this.sellerRating = sellerRating;
        this.bidderRating = bidderRating;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public String getLocation() {
        return location;
    }

    public String getTaxRegistrationNumber() {
        return taxRegistrationNumber;
    }

    public int getSellerRating() {
        return sellerRating;
    }

    public int getBidderRating() {
        return bidderRating;
    }
}
