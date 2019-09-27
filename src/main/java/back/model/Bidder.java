package back.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class Bidder {
    private int id;
    private String username;
    private int rating;
    private String location;
    private String country;

    public Bidder(){}

    public Bidder(String username, int rating, String location, String country) {
        this.username = username;
        this.rating = rating;
        this.location = location;
        this.country = country;
    }

    public Bidder(int id, String username, int rating, String location, String country) {
        this.id = id;
        this.username = username;
        this.rating = rating;
        this.location = location;
        this.country = country;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlAttribute(name="UserID")
    public String getUsername() {
        return username;
    }

    @XmlAttribute(name="Rating")
    public int getRating() {
        return rating;
    }

    @XmlElement(name="Location")
    public String getLocation() {
        return location;
    }

    @XmlElement(name="Country")
    public String getCountry() {
        return country;
    }

    @XmlTransient
    public int getId() {
        return id;
    }
}
