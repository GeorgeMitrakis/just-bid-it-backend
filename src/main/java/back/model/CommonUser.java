package back.model;

public class CommonUser extends  User {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final String country;
    private final String location;
    private final String taxRegistrationNumber;
    private final int sellerRating;
    private final int bidderRating;

    public CommonUser(long id, String username, String access, String firstName, String lastName, String email, String phoneNumber, String country, String location, String taxRegistrationNumber, int sellerRating, int bidderRating) {
        super(id, username, "common user", access);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.location = location;
        this.taxRegistrationNumber = taxRegistrationNumber;
        this.sellerRating = sellerRating;
        this.bidderRating = bidderRating;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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
