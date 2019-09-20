package back.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;
import java.util.List;

public class Location {
    private String name;

    private Double latitude;
    private Double longitude;

//    private List<Item> itemsInThisLocation;
//    private List<Item> usersInThisLocation;

    public Location(String name) {
        this.name = name;
    }

    public Location(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @XmlValue
    public String getName() {
        return name;
    }

    @XmlAttribute(name="Latitude")
    public Double getLatitude() {
        return latitude;
    }

    @XmlAttribute(name="Longitude")
    public Double getLongitude() {
        return longitude;
    }

    //    public List<Item> getItemsInThisLocation() {
//        return itemsInThisLocation;
//    }
//
//    public List<Item> getUsersInThisLocation() {
//        return usersInThisLocation;
//    }
//
//    public void setItemsInThisLocation(List<Item> itemsInThisLocation) {
//        this.itemsInThisLocation = itemsInThisLocation;
//    }
//
//    public void setUsersInThisLocation(List<Item> usersInThisLocation) {
//        this.usersInThisLocation = usersInThisLocation;
//    }
}
